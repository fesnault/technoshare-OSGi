package org.phoenix.osgi.bundles.uses.bar;

import org.phoenix.osgi.bundles.uses.foo.Foo;

public class Bar {
	public Bar() {
		super();
		System.out.println("Constructing B");
	}
	
	public Foo getFoo() {
		return new Foo();
	}
}
