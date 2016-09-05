package spring;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockTest implements Runnable {
	@SuppressWarnings("unused")
	private Lock lock = new ReentrantLock();
	private List<Integer> arrayList = new ArrayList<Integer>();
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		get(Thread.currentThread());
	}

	public static void main(String[] args) {
		LockTest my = new LockTest();
		new Thread(my, "C").start();// 同一个mt，但是在Thread中就不可以，如果用同一个实例化对象mt，就会出现异常
		new Thread(my, "b").start();// 同一个mt，但是在Thread中就不可以，如果用同一个实例化对象mt，就会出现异常

	}

	public void get(Thread thread) {
		rwl.readLock().lock();
		try {
			long start = System.currentTimeMillis();

			while (System.currentTimeMillis() - start <= 1) {
				System.out.println(thread.getName() + "正在进行读操作");
			}
			System.out.println(thread.getName() + "读操作完毕");
		} finally {
			rwl.readLock().unlock();
		}
	}

	public void insert(Thread thread) {
		if (lock.tryLock()) {
			try {
				System.out.println(thread.getName() + "得到了锁");
				for (int i = 0; i < 5; i++) {
					arrayList.add(i);
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				System.out.println(thread.getName() + "释放了锁");
				lock.unlock();
			}
		} else {
			System.out.println(thread.getName() + "获取锁失败");
		}
	}
}
