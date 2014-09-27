package org.adligo.tests4j.shared.asserts.dependency;


public enum CircularDependencies implements I_CircularDependencies {
	None(0), InnerClasses(1), WithInPackage(2);
	/**
	 * Circular Dependencies are not allowed in the souceClass.
	 */
	public static final int Na = 0; 
	/**
	 * Allow circular dependencies of the sourceClass to exist in the inner or outer class,
	 *   or in other words the $X part of the class name is removed during
	 *   the comparison of class names.  Circular dependencies are NOT allowed
	 *   between the java package of the sourceClass and other java packages.
	 */
	public static final int AllowInnerOuterClasses = 1; 
	/**
	 * Allow circular dependencies of the sourceClass to exist with in the package,
	 *    so that org.example.foo.Bar can refer to org.example.foo.Foo
	 *    and or vice versa.   Circular dependencies are NOT allowed
	 *    between the sourceClass package and other java packages.
	 */
	public static final int AllowInPackage = 2;
	
	private int id;
	
	private CircularDependencies(int p) {
		id = p;
	}
	
	public int getId() {
		return id;
	}
	
	public static I_CircularDependencies get(int p) {
		switch(p) {
			case AllowInnerOuterClasses:
				return InnerClasses;
			case AllowInPackage:
				return WithInPackage;
			default:
				return None;
		}
	}
	
	public static CircularDependencies get(I_CircularDependencies p) {
		return (CircularDependencies) get(p.getId());
	}
}
