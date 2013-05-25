package org.yshi.frp;

import java.util.Observable;
import java.util.Observer;

import org.yshi.frp.func.BinaryFunction;
import org.yshi.frp.func.UnaryFunction;

public class MutableValue<T> extends Observable {	
	private T value;
	
	public MutableValue(T initial) {
		super();
		this.value = initial;
	}
	
	public MutableValue(MutableValue<T> source) {
		this.value = source.get();
		source.addObserver(new IdentityObserver<T>(this));
	}
	
	public <PT> MutableValue(
			final MutableValue<PT> source1,
			final MutableValue<PT> source2,
			final BinaryFunction<PT, PT, T> join
	) {
		final MutableValue<T> self = this;
		Observer observer = new Observer() {
			public void update(Observable o, Object arg) {
				self.set(join.evaluate(source1.get(), source2.get()));
			}
		};
		this.value = join.evaluate(source1.get(), source2.get());
		source1.addObserver(observer);
		source2.addObserver(observer);
	}
	
	public void set(T new_value) {
		setChanged();
		this.value = new_value;
		notifyObservers();
		clearChanged();
	};
	
	public void set(final MutableValue<T> source) {
		final MutableValue<T> self = this;
		source.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				self.set(source.get());
			}
		});
		this.set(source.get());
	}

	public T get() {
		return this.value;
	};
	
	public <NT> MutableValue<NT> transform(UnaryFunction<T, NT> map) {
		MutableValue<NT> retval = new MutableValue<NT>(map.evaluate(this.value));
		addObserver(new MappingObserver<T, NT>(retval, map));
		return retval;
	}
	
	private static class IdentityObserver<T> implements Observer {
		private MutableValue<T> sink;
		
		public IdentityObserver(MutableValue<T> sink) {
			this.sink = sink;
		}
		
		@SuppressWarnings("unchecked")
		public void update(Observable obs, Object arg) {
			this.sink.set(((MutableValue<T>) obs).get());
		}
	}
	
	private static class MappingObserver<T, NT> implements Observer {
		private MutableValue<NT> sink;
		private UnaryFunction<T, NT> map;
		
		public MappingObserver(MutableValue<NT> sink, UnaryFunction<T, NT> map) {
			this.sink = sink;
			this.map = map;
		}
		
		@SuppressWarnings("unchecked")
		public void update(Observable obs, Object arg) {
			this.sink.set(this.map.evaluate(((MutableValue<T>) obs).get()));
		}
	}
}
