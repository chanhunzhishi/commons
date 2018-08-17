package cn.chanhunzhishi.transform.current;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 带有缓存的日期生成工具，可以
 */
public class CachedDateUtil {
	private static CachedDateUtil instance;
	private Map<String, CachedDateBean> cachedDate;

	/**
	 * 获取一个缓存的格式化日期时间
	 *
	 */
	public String getCachedDate(String pattern) {
		CachedDateBean cachedDateBean = cachedDate.get(pattern);
		if (cachedDateBean == null) {
			throw new RuntimeException("该类型没有被缓存");
		}
		long currentTime = System.currentTimeMillis();
		if (currentTime - (currentTime % cachedDateBean.getMaxCachedMills()) != cachedDateBean.getLastTime()) {
			synchronized (cachedDateBean.getLockFlag()) {
				if ((currentTime - cachedDateBean.getLastTime()) > cachedDateBean.getMaxCachedMills()) {
					cachedDateBean.setCache(cachedDateBean.getDateFormat().format(currentTime));
					cachedDateBean.setLastTime(currentTime);
				}
			}
		}
		return cachedDateBean.getCache();
	}

	/**
	 * 允许更新，添加新的格式化类型
	 *
	 * @param pattern
	 * @param maxCachedMillis
	 */
	public void addCachedPattern(String pattern, long maxCachedMillis) {
		if (pattern == null || pattern.length() == 0 || maxCachedMillis < 1) {
			throw new CacheDateException("参数错误");
		}

		for (DateScale dateScale : DateScale.values()) {
			if (dateScale.getPattern().equals(pattern)) {
				throw new CacheDateException("初始参数暂时不允许更新");
			}
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		long l = System.currentTimeMillis();
		l = l - (l % maxCachedMillis);
		simpleDateFormat.format(l);
		synchronized (CachedDateUtil.class) {
			Map<String, CachedDateBean> tmpMap = new HashMap<>(cachedDate);
			tmpMap.put(pattern, new CachedDateBean(l, maxCachedMillis, simpleDateFormat));
			cachedDate = tmpMap;
		}
	}

	private CachedDateUtil() {
		cachedDate = new HashMap<>();
		long l = System.currentTimeMillis();
		for (DateScale dateScale : DateScale.values()) {
			cachedDate.put(dateScale.getPattern(), new CachedDateBean(l, dateScale.getMaxCatchMill(), new SimpleDateFormat(dateScale.getPattern())));
		}
	}

	public static CachedDateUtil getInstance() {
		if (instance == null) {
			synchronized (CachedDateUtil.class) {
				if (instance == null) {
					instance = new CachedDateUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 存储缓存格式化日期的
	 */
	private class CachedDateBean {
		private CachedDateBean(long lastTime, long maxCachedMills, SimpleDateFormat dateFormat) {
			this.lastTime = lastTime;
			this.maxCachedMills = maxCachedMills;
			this.dateFormat = dateFormat;
			cache = dateFormat.format(lastTime);
			lockFlag = cache;
		}

		private final SimpleDateFormat dateFormat;
		private final String lockFlag;
		private String cache;
		private long lastTime;
		private long maxCachedMills;

		private long getMaxCachedMills() {
			return maxCachedMills;
		}

		private long getLastTime() {
			return lastTime;
		}

		private String getCache() {
			return cache;
		}

		private String getLockFlag() {
			return lockFlag;
		}

		private void setCache(String cache) {
			this.cache = cache;
		}

		private void setLastTime(long lastTime) {
			this.lastTime = lastTime;
		}

		private SimpleDateFormat getDateFormat() {
			return dateFormat;
		}
	}

	public enum DateScale {
		FULL_MILLIS_DATE_TIME("yyyy-MM-dd HH:mm:ss.SSS", 1),
		DATE_SECOND_TIME("yyyy-MM-dd HH:mm:ss", 1000),
		DATE_MOMENT_TIME("yyyy-MM-dd HH:mm", 1000 * 60),
		DATE("yyyy-MM-dd", 1000 * 60 * 60 * 24);
		private final String pattern;
		private final long maxCatchMill;

		DateScale(String pattern, long maxCatchMill) {
			this.maxCatchMill = maxCatchMill;
			this.pattern = pattern;
		}

		public String getPattern() {
			return pattern;
		}

		public long getMaxCatchMill() {
			return maxCatchMill;
		}
	}
}
