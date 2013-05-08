package org.ratpackframework.session.store;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class DefaultSessionStorage implements SessionStorage {

  private final ConcurrentMap<String, Object> delegate;

  public DefaultSessionStorage(ConcurrentMap<String, Object> delegate) {
    this.delegate = delegate;
  }

  @Override
  public Object putIfAbsent(String key, Object value) {
    return delegate.putIfAbsent(key, value);
  }

  @Override
  public boolean remove(Object key, Object value) {
    return delegate.remove(key, value);
  }

  @Override
  public boolean replace(String key, Object oldValue, Object newValue) {
    return delegate.replace(key, oldValue, newValue);
  }

  @Override
  public Object replace(String key, Object value) {
    return delegate.replace(key, value);
  }

  @Override
  public int size() {
    return delegate.size();
  }

  @Override
  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return delegate.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return delegate.containsValue(value);
  }

  @Override
  public Object get(Object key) {
    return delegate.get(key);
  }

  @Override
  public Object put(String key, Object value) {
    return delegate.put(key, value);
  }

  @Override
  public Object remove(Object key) {
    return delegate.remove(key);
  }

  @Override
  public void putAll(Map<? extends String, ?> m) {
    delegate.putAll(m);
  }

  @Override
  public void clear() {
    delegate.clear();
  }

  @Override
  public Set<String> keySet() {
    return delegate.keySet();
  }

  @Override
  public Collection<Object> values() {
    return delegate.values();
  }

  @Override
  public Set<Entry<String,Object>> entrySet() {
    return delegate.entrySet();
  }

  @Override
  public boolean equals(Object o) {
    return delegate.equals(o);
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }
}
