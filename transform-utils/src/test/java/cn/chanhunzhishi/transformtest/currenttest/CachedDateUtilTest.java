package cn.chanhunzhishi.transformtest.currenttest;

import cn.chanhunzhishi.transform.current.CachedDateUtil;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CachedDateUtilTest {
	@Test
	public void simpleTimeTest() {
		long l = System.currentTimeMillis();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < 100000000; i++) {
			String format = simpleDateFormat.format(System.currentTimeMillis());
		}
		System.out.println(System.currentTimeMillis() - l);
	}

	@Test
	public void cachedTestTime() {
		long l = System.currentTimeMillis();
		String[] array = new String[100000000];
		for (int i = 0; i < 100000000; i++) {
			String format = CachedDateUtil.getInstance().getCachedDate(CachedDateUtil.DateScale.FULL_MILLIS_DATE_TIME.getPattern());
			array[i] = format;
		}
		System.out.println(System.currentTimeMillis() - l);
		for(int i=0;i<100000000;i++){
			if(i%10000000==0){
				System.out.println(array[i]);
			}
		}
	}
	@Test
	public void addCached(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("123123");
		String format = simpleDateFormat.format(System.currentTimeMillis());
		System.out.println(format);
	}


	@Test
	public void testTime() {
		class MyThread implements Runnable {
			public MyThread(CachedDateUtil cachedDate, long startTime) {
				this.cachedDate = cachedDate;
				this.startTime = startTime;
			}

			private long startTime;
			private CachedDateUtil cachedDate;
			private List<String> list = new ArrayList<>();

			@Override
			public void run() {
				for (int i = 0; i < 100000; i++) {
					list.add(cachedDate.getCachedDate(CachedDateUtil.DateScale.FULL_MILLIS_DATE_TIME.getPattern()));
				}
				System.out.println("结束时间" + System.currentTimeMillis() + " 运行时间" + (System.currentTimeMillis() -startTime ));
			}
		}
		List<MyThread> threadList = new ArrayList<>();
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			threadList.add(new MyThread(CachedDateUtil.getInstance(), time1));
		}

		for (MyThread myThread : threadList) {
			new Thread(myThread).start();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
