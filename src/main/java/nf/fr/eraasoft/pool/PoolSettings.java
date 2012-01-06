package nf.fr.eraasoft.pool;

import nf.fr.eraasoft.pool.impl.PoolControler;
import nf.fr.eraasoft.pool.impl.PoolFactory;

/**
 * 
 * @author eddie
 * 
 * @param <T>
 */
public class PoolSettings<T> {
	/**
	 * Wait (in second) before
	 */
	public static final int DEFAUL_MAX_WAIT = 5;
	public static final int DEFAULT_MIN = 1;
	public static final int DEFAULT_MAX = 10;

	private int maxWait = DEFAUL_MAX_WAIT;
	private int min = DEFAULT_MIN;
	private int max = DEFAULT_MAX;
	private int maxIdle = min;
	public boolean validateWhenReturn = false;

	private final PoolFactory<T> poolFactory;

	public PoolSettings(final PoolableObject<T> poolableObject) {
		this.poolFactory = new PoolFactory<T>(this, poolableObject);
		PoolControler.addPoolSettings(this);
	}

	public ObjectPool<T> pool() {
		return poolFactory.getPool();
	}

	public PoolSettings<T> maxIdle(final int maxIdle) {
		this.maxIdle = maxIdle < min ? min : maxIdle;
		return this;
	}

	public int maxIdle() {
		return this.maxIdle;
	}

	public PoolSettings<T> maxWait(final int maxWait) {
		this.maxWait = maxWait;
		return this;
	}

	public PoolSettings<T> min(final int min) {
		this.min = min;
		if (maxIdle < min) {
			maxIdle = min;
		}
		if (max>0 && min > max) {
			max(min);
		}
		return this;
	}

	/**
	 * if  
	 * @param max
	 * @return
	 */
	public PoolSettings<T> max(final int max) {
		this.max = max;
		if (max>0 && max < min) {
			min(max);
		}
		return this;
	}

	public int min() {
		return min;
	}

	public int maxWait() {
		return maxWait;
	}

	public int max() {
		return max;
	}

	public static void shutdown() {
		PoolControler.shutdown();
	}
	
	/**
	 * if true invoke PoolableObject.validate() method
	 * @param validateWhenReturn
	 * @return
	 */
	public PoolSettings<T> validateWhenReturn(boolean validateWhenReturn) {
		this.validateWhenReturn = validateWhenReturn;
		return this;
	}
	
	

}
