package smart.league.project.util.map;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * This class represents a map of String keys to a List of String values.
 * 
 * It's useful to represent things like HTTP headers and HTTP parameters which allow multiple values
 * for keys.
 *
 * @author Razvan Prichici
 */
public class MultiValueMap<K, V> implements Map<K, Deque<V>>, Serializable {

	private static final long serialVersionUID = 1L;

	private final transient Map<K, Deque<V>> targetMap;


	public MultiValueMap() {
		this.targetMap = new LinkedHashMap<>();
	}

	public MultiValueMap(int initialCapacity) {
		this.targetMap = new LinkedHashMap<>(initialCapacity);
	}

	public MultiValueMap(Map<K, Deque<V>> otherMap) {
		this.targetMap = new LinkedHashMap<>(otherMap);
	}

	/**
	 * Return the first value for the given key.
	 * 
	 * @param key the key
	 * @return the first value for the specified key, or {@code null}
	 */
	public V getFirst(K key) {
		Deque<V> values = this.targetMap.get(key);
		return (values != null ? values.peek() : null);
	}

	/**
	 * Add a value to the current list of values for the supplied key.
	 * 
	 * @param key the key
	 * @param value the value to be added
	 */
	public void add(K key, V value) {
		if (value != null) {
			this.targetMap.computeIfAbsent(key, k -> new ArrayDeque<>(5)).add(value);
		}

	}

	/**
	 * Add the given values to the current list of values for the given key.
	 * 
	 * @param key the key
	 * @param value the value to be added
	 */
	public void add(K key, V[] value) {
		List<V> values = Arrays.asList(value);
		values.removeIf(p -> p == null);
		this.targetMap.computeIfAbsent(key, k -> new ArrayDeque<>(5)).addAll(values);
	}

	/**
	 * Add the given values to the current list of values for the given key.
	 * 
	 * @param key the key
	 * @param value the value to be added
	 */
	public void add(K key, List<V> value) {
		value.removeIf(p -> p == null);
		this.targetMap.computeIfAbsent(key, k -> new ArrayDeque<>(5)).addAll(value);
	}


	/**
	 * Set the given single value under the given key.
	 * 
	 * @param key the key
	 * @param value the value to set
	 */
	public void set(K key, V value) {		
		if (value != null) {
			this.targetMap.compute(key, (k, v) -> new ArrayDeque<>(5)).add(value);
		}
	}

	public boolean contains(K key, V value) {
		return this.targetMap.getOrDefault(key, new ArrayDeque<>(5)).contains(value);
	}





	// Map implementation

	@Override
	public int size() {
		return this.targetMap.size();
	}

	@Override
	public boolean isEmpty() {
		return this.targetMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.targetMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.targetMap.containsValue(value);
	}

	@Override
	public Deque<V> get(Object key) {
		return this.targetMap.get(key);
	}

	@Override
	public Deque<V> put(K key, Deque<V> value) {
		return this.targetMap.put(key, value);
	}

	@Override
	public Deque<V> remove(Object key) {
		return this.targetMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends Deque<V>> map) {
		this.targetMap.putAll(map);
	}

	@Override
	public void clear() {
		this.targetMap.clear();
	}

	@Override
	public Set<K> keySet() {
		return this.targetMap.keySet();
	}

	@Override
	public Collection<Deque<V>> values() {
		return this.targetMap.values();
	}

	@Override
	public Set<Entry<K, Deque<V>>> entrySet() {
		return this.targetMap.entrySet();
	}

	@Override
	public boolean equals(Object obj) {
		return this.targetMap.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.targetMap.hashCode();
	}

	@Override
	public String toString() {
		return this.targetMap.toString();
	}

	public Stream<V> flatMap() {
		return targetMap.values().stream().flatMap(Deque<V>::stream);
	}



}
