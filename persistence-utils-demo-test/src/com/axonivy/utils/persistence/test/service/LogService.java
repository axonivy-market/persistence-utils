package com.axonivy.utils.persistence.test.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LogService {
	private static final String LOGFORMAT = "%d{ABSOLUTE} %-5level %c %X %msg%n%throwable";
	private static final LogService INSTANCE = new LogService();
	private static final String INDENT = "    ";

	public static LogService get() {
		return INSTANCE;
	}

	/**
	 * Create a logging setup for console only and set logger levels.
	 *
	 * @param loggerLevels
	 */
	public void consoleLog(LoggerLevel... loggerLevels) {
		consoleLog(null, List.of(loggerLevels));
	}

	/**
	 * Create a logging setup for console only and set root level and logger levels.
	 *
	 * @param globalLevel
	 * @param loggerLevels
	 */
	public void consoleLog(Level globalLevel, LoggerLevel... loggerLevels) {
		consoleLog(globalLevel, List.of(loggerLevels));
	}

	/**
	 * Create a logging setup for console only and set root level and logger levels.
	 *
	 * @param globalLevel if null, will be set to warning
	 * @param loggerNames
	 */
	public void consoleLog(Level globalLevel, Collection<LoggerLevel> loggerNames) {
		String name = "stdout";

		appenderLog(c -> ConsoleAppender.newBuilder()
				.setName(name)
				.setLayout(PatternLayout.newBuilder()//
						.withPattern(LOGFORMAT)
						.withConfiguration(c)
						.build())
				.setConfiguration(c)
				.build(), globalLevel, loggerNames);
	}

	/**
	 * Create a logging setup for a {@link ByteArrayOutputStream} only and set root level and logger levels.
	 *
	 * @param globalLevel if null, will be set to warning
	 * @param loggerLevels
	 * @return
	 */
	public LogCapturer captureLog(Level globalLevel, Collection<LoggerLevel> loggerLevels) {
		LogCapturer logCapturer = new LogCapturer();

		Function<Configuration, Appender> appSup =
				streamAppenderSupplier(
						"OutputStream",
						logCapturer,
						LOGFORMAT);

		Appender appender = appenderLog(appSup, globalLevel, loggerLevels);

		logCapturer.setAppender(appender);

		return logCapturer;
	}

	/**
	 * Create a supplier for a console appender.
	 *
	 * @param pattern
	 * @return
	 */
	public Function<Configuration, Appender> consoleAppenderSupplier(String pattern) {
		return c -> ConsoleAppender.newBuilder()
				.setName("stdout")
				.setLayout(PatternLayout.newBuilder().withPattern(pattern).withConfiguration(c).build())
				.setConfiguration(c)
				.build();
	}

	/**
	 * Create a supplier for an output stream appender.
	 *
	 * Such appenders can be used to test logging of functions.
	 *
	 * @param name
	 * @param stream
	 * @param pattern
	 * @return
	 */
	public Function<Configuration, Appender> streamAppenderSupplier(String name, OutputStream stream, String pattern) {
		return c -> OutputStreamAppender.newBuilder()
				.setName(name)
				.setTarget(stream)
				.setLayout(PatternLayout.newBuilder().withPattern(pattern).withConfiguration(c).build())
				.setConfiguration(c)
				.build();
	}

	/**
	 * Create a logger configuration where all logging is going to the appender supplied by the passed appender supplier.
	 *
	 * @param appenderSupplier
	 * @param globalLevel
	 * @param loggerLevels
	 */
	public Appender appenderLog(Function<Configuration, Appender> appenderSupplier, Level globalLevel, Collection<LoggerLevel> loggerLevels) {
		BuiltConfiguration configuration = ConfigurationBuilderFactory.newConfigurationBuilder().build();
		LoggerContext loggerContext = getLoggerContext();

		Appender appender = appenderSupplier.apply(configuration);
		appender.start();
		configuration.addAppender(appender);

		LoggerConfig rootLogger = configuration.getRootLogger();
		rootLogger.setLevel(globalLevel == null ? Level.WARN : globalLevel);
		for (String loggerName : rootLogger.getAppenders().keySet()) {
			rootLogger.removeAppender(loggerName);
		}
		rootLogger.addAppender(appender, null, null);

		if(loggerLevels != null) {
			for (LoggerLevel loggerLevel : loggerLevels) {
				LoggerConfig loggerConfig = LoggerConfig.newBuilder()
						.withAdditivity(loggerLevel.isAdditivity())
						.withLevel(loggerLevel.getLevel())
						.withLoggerName(loggerLevel.getName())
						.withConfig(configuration)
						.build();
				loggerConfig.addAppender(appender, null, null);
				configuration.addLogger(loggerLevel.getName(), loggerConfig);
				loggerConfig.start();
			}
		}

		Configurator.reconfigure(configuration);
		loggerContext.updateLoggers();

		return appender;
	}

	/**
	 * Create, add and start a new Appender for current logging configuration.
	 *
	 * @param appenderSupplier
	 * @return
	 */
	public Appender createAppender(Function<Configuration, Appender> appenderSupplier) {
		LoggerContext loggerContext = getLoggerContext();
		Configuration configuration = loggerContext.getConfiguration();

		Appender appender = appenderSupplier.apply(configuration);
		appender.start();
		configuration.addAppender(appender);

		return appender;
	}

	/**
	 * @return
	 */
	public LoggerContext getLoggerContext() {
		return LoggerContext.getContext();
	}

	/**
	 * Add loggers logging to given appender of current logging configuration.
	 *
	 * @param appender
	 * @param loggerLevels
	 */
	public void addLoggers(Appender appender, Collection<LoggerLevel> loggerLevels) {
		LoggerContext loggerContext = getLoggerContext();
		Configuration configuration = loggerContext.getConfiguration();
		addLoggers(configuration, appender, loggerLevels);
		loggerContext.updateLoggers(configuration);
	}

	/**
	 * Add loggers logging to given appender of given logging configuration.
	 *
	 * @param configuration
	 * @param appender
	 * @param loggerLevels
	 */
	public void addLoggers(Configuration configuration, Appender appender, Collection<LoggerLevel> loggerLevels) {
		for (LoggerLevel loggerLevel : loggerLevels) {
			// remove, if already there
			configuration.removeLogger(loggerLevel.getName());
			LoggerConfig loggerConfig = LoggerConfig.newBuilder()
					.withAdditivity(true)
					.withLevel(loggerLevel.getLevel())
					.withLoggerName(loggerLevel.getName())
					.withConfig(configuration)
					.build();
			loggerConfig.addAppender(appender, null, null);
			configuration.addLogger(loggerLevel.getName(), loggerConfig);
			loggerConfig.start();
		}
	}

	/**
	 * Create a log capturer based on a {@link ByteArrayOutputStream}.
	 *
	 * @param loggerLevels
	 * @return
	 */
	public LogCapturer addLogCapturer(LoggerLevel...loggerLevels) {
		LogCapturer logCapturer = new LogCapturer();

		Appender appender = createAppender(streamAppenderSupplier(UUID.randomUUID().toString(), logCapturer, LOGFORMAT));
		addLoggers(appender, List.of(loggerLevels));

		return logCapturer;
	}

	public static class LogCapturer extends ByteArrayOutputStream {
		private Appender appender;

		private LogCapturer() {
			super();
		}

		private void setAppender(Appender appender) {
			this.appender = appender;
		}

		public Appender getAppender() {
			return appender;
		}

		@Override
		public void close() {
			try {
				super.close();
			} catch (IOException e) {
				throw new RuntimeException("Unexpected exception while closing log capturer.", e);
			}
		}
	}

	/**
	 * Container for logger and level.
	 *
	 * Note: additivity is turned off by default. Nevertheless you can turn it on explicitly.
	 */
	public static class LoggerLevel {
		/**
		 * Show Hibernate statements.
		 */
		public static final LoggerLevel HIBERNATE_SQL_STATEMENTS = forName("org.hibernate.SQL", Level.TRACE);

		/**
		 * Show Hibernate parameters (except Enums).
		 */
		public static final LoggerLevel HIBERNATE_SQL_PARAMETERS = forName("org.hibernate.type.descriptor.sql.BasicBinder", Level.TRACE);

		/**
		 * Show Hibernate enum parameters.
		 */
		public static final LoggerLevel HIBERNATE_SQL_ENUMS = forName("org.hibernate.type.EnumType", Level.TRACE);

		/**
		 * Show Hibernate results.
		 */
		public static final LoggerLevel HIBERNATE_SQL_RESULTS = forName("org.hibernate.type.descriptor.sql.BasicExtractor", Level.TRACE);

		/**
		 * Show everything from Hibernate.
		 */
		public static final LoggerLevel[] HIBERNATE_FULL= new LoggerLevel[] {
				HIBERNATE_SQL_STATEMENTS,
				HIBERNATE_SQL_PARAMETERS,
				HIBERNATE_SQL_ENUMS,
				HIBERNATE_SQL_RESULTS
		};

		private String name;
		private Level level;
		private boolean additivity = false;

		/**
		 * Create a package level.
		 *
		 * @param name
		 * @param level
		 * @return
		 */
		public static LoggerLevel forName(String name, Level level) {
			return new LoggerLevel(name, level);
		}

		/**
		 * Create a package level for an object.
		 *
		 * @param object
		 * @param level
		 * @return
		 */
		public static LoggerLevel forObject(Object object, Level level) {
			return forName(object.getClass().getCanonicalName(), level);
		}

		/**
		 * Create a package level for a class.
		 *
		 * @param class
		 * @param level
		 * @return
		 */
		public static LoggerLevel forClass(Class<?> clazz, Level level) {
			return forName(clazz.getCanonicalName(), level);
		}

		/**
		 * Create a package level for a package.
		 *
		 * @param clazz
		 * @param strip number of packages to strip from the end
		 * @param level
		 * @return
		 */
		public static LoggerLevel forPackage(Class<?> clazz, int strip, Level level) {
			String pckName = clazz.getCanonicalName();
			while(strip-- > 0) {
				int i = pckName.lastIndexOf(".");
				if(i >= 0) {
					pckName = pckName.substring(0, i);
				}
			}
			return forName(pckName, level);
		}

		/**
		 * Combine LoggerLevels.
		 *
		 * @param left
		 * @param right
		 * @return
		 */
		public static LoggerLevel[] combine(LoggerLevel[] left, LoggerLevel[] right) {
			return Stream.concat(Stream.of(left), Stream.of(right)).toArray(LoggerLevel[]::new);
		}

		/**
		 * Combine LoggerLevels.
		 *
		 * @param left
		 * @param right
		 * @return
		 */
		public static LoggerLevel[] combine(LoggerLevel left, LoggerLevel[] right) {
			return Stream.concat(Stream.of(left), Stream.of(right)).toArray(LoggerLevel[]::new);
		}

		/**
		 * Combine LoggerLevels.
		 *
		 * @param left
		 * @param right
		 * @return
		 */
		public static LoggerLevel[] combine(LoggerLevel[] left, LoggerLevel right) {
			return Stream.concat(Stream.of(left), Stream.of(right)).toArray(LoggerLevel[]::new);
		}

		/**
		 * Combine LoggerLevels.
		 *
		 * @param left
		 * @param right
		 * @return
		 */
		public static LoggerLevel[] combine(LoggerLevel left, LoggerLevel right) {
			return Stream.concat(Stream.of(left), Stream.of(right)).toArray(LoggerLevel[]::new);
		}

		private LoggerLevel(String name, Level level) {
			this.name = name;
			this.level = level;
		}

		public Level getLevel() {
			return level;
		}

		public void setLevel(Level level) {
			this.level = level;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}


		public LoggerLevel withAdditivity(boolean additivity) {
			this.additivity = additivity;
			return this;
		}

		/**
		 * @return the additivity
		 */
		public boolean isAdditivity() {
			return additivity;
		}

		/**
		 * @param additivity the additivity to set
		 */
		public void setAdditivity(boolean additivity) {
			this.additivity = additivity;
		}
	}

	/**
	 * Describe the logging configuration.
	 *
	 * @return
	 */
	public String describeLogging() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		LoggerContext context = LoggerContext.getContext(true);
		Configuration configuration = context.getConfiguration();

		pw.println(describeContext(context));
		pw.println(describeConfiguration(configuration));
		return sw.toString();
	}


	public String describeContext(LoggerContext loggerContext) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String indent = INDENT;

		pw.println("Logger Context");
		pw.print(indent);
		pw.println("Loggers");

		describeLogger(indent + INDENT + ">", pw, loggerContext.getRootLogger());

		for (Logger logger : loggerContext.getLoggers()) {
			describeLogger(indent + INDENT, pw, logger);
		}

		return sw.toString();
	}

	public String describeConfiguration(Configuration configuration) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String indent = INDENT;

		pw.println("Logger Configuration");

		for (Entry<String, Appender> entry : configuration.getAppenders().entrySet()) {
			describeAppender(indent, pw, entry.getValue());
		}

		describeLoggerConfig(indent + ">", pw, configuration.getRootLogger());

		for (Entry<String, LoggerConfig> entry : configuration.getLoggers().entrySet()) {
			describeLoggerConfig(indent, pw, entry.getValue());
		}

		return sw.toString();
	}

	/**
	 * @param pw
	 * @param loggerConfig
	 */
	public void describeLoggerConfig(String indent, PrintWriter pw, LoggerConfig loggerConfig) {
		pw.print(indent);
		pw.printf("LoggerConfig: %-40s: %s (%08x)%n", loggerConfig.getName(), loggerConfig.getLevel(), System.identityHashCode(loggerConfig));
		for (Entry<String, Appender> entry : loggerConfig.getAppenders().entrySet()) {
			describeAppender(indent + INDENT, pw, entry.getValue());
		}
	}

	/**
	 * @param pw
	 * @param logger
	 */
	public void describeLogger(String indent, PrintWriter pw, Logger logger) {
		pw.print(indent);
		pw.printf("Logger: %-40s: %s (%08x)%n", logger.getName(), logger.getLevel(), System.identityHashCode(logger));
		for (Entry<String, Appender> entry : logger.getAppenders().entrySet()) {
			describeAppender(indent + INDENT, pw, entry.getValue());
		}
	}

	/**
	 * @param pw
	 * @param entry
	 */
	public void describeAppender(String indent, PrintWriter pw, Appender appender) {
		pw.print(indent);
		pw.printf("Appender: %-40s (%08x)%n", appender, System.identityHashCode(appender));
	}
}
