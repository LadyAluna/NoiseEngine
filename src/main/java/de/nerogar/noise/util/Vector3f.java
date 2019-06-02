package de.nerogar.noise.util;

public class Vector3f implements Vectorf<Vector3f> {

	private static final float SQRT_3 = (float) Math.sqrt(3.0);

	private float x;

	private float y;
	private float z;
	private float value;
	private boolean isValueDirty = true;

	//constructors
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		isValueDirty = true;
	}

	public Vector3f(float xyz) {
		set(xyz);
	}

	public Vector3f() {
	}

	//constructor for cloning
	private Vector3f(float x, float y, float z, float value, boolean isValueDirty) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.value = value;
		this.isValueDirty = isValueDirty;
	}

	@Override
	public Vector3f newInstance() {
		return new Vector3f();
	}

	@Override
	public int getComponentCount() {
		return 3;
	}

	//get
	@Override
	public float get(int component) {
		switch (component) {
			case 0:
				return x;
			case 1:
				return y;
			case 2:
				return z;
			default:
				return 0f;
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	//set
	@Override
	public Vector3f set(int component, float f) {
		switch (component) {
			case 0:
				x = f;
				break;
			case 1:
				y = f;
				break;
			case 2:
				z = f;
				break;
		}
		isValueDirty = true;
		return this;
	}

	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		isValueDirty = true;
		return this;
	}

	public Vector3f setX(float x) {
		this.x = x;
		isValueDirty = true;
		return this;
	}

	public Vector3f setY(float y) {
		this.y = y;
		isValueDirty = true;
		return this;
	}

	public Vector3f setZ(float z) {
		this.z = z;
		isValueDirty = true;
		return this;
	}

	@Override
	public Vector3f set(float xyz) {
		x = xyz;
		y = xyz;
		z = xyz;
		setValueCache(Math.abs(xyz) * SQRT_3);
		return this;
	}

	@Override
	public Vector3f set(Vectorf<?> v) {
		x = v.get(0);
		y = v.get(1);
		z = v.get(2);
		isValueDirty = true;
		return this;
	}

	//add
	@Override
	public Vector3f add(int component, float f) {
		switch (component) {
			case 0:
				x += f;
				break;
			case 1:
				y += f;
				break;
			case 2:
				z += f;
				break;
		}
		isValueDirty = true;
		return this;
	}

	public Vector3f addX(float x) {
		this.x += x;
		isValueDirty = true;
		return this;
	}

	public Vector3f addY(float y) {
		this.y += y;
		isValueDirty = true;
		return this;
	}

	public Vector3f addZ(float z) {
		this.z += z;
		isValueDirty = true;
		return this;
	}

	@Override
	public Vector3f add(Vectorf<?> v) {
		x += v.get(0);
		y += v.get(1);
		z += v.get(2);
		isValueDirty = true;
		return this;
	}

	@Override
	public Vector3f added(Vectorf<?> v) {
		return clone().add(v);
	}

	//subtract
	@Override
	public Vector3f subtract(Vectorf<?> v) {
		x -= v.get(0);
		y -= v.get(1);
		z -= v.get(2);
		isValueDirty = true;
		return this;
	}

	@Override
	public Vector3f subtracted(Vectorf<?> v) {
		return clone().subtract(v);
	}

	//multiply
	@Override
	public Vector3f multiply(float f) {
		value *= f;
		x *= f;
		y *= f;
		z *= f;
		return this;
	}

	@Override
	public Vector3f multiplied(float f) {
		return clone().multiply(f);
	}

	//tools
	@Override
	public float dot(Vectorf<?> v) {
		return x * v.get(0) + y * v.get(1) + z * v.get(2);
	}

	public Vector3f cross(Vector3f v) {
		set(
				y * v.getZ() - z * v.getY(),
				z * v.getX() - x * v.getZ(),
				x * v.getY() - y * v.getX()
		   );

		return this;
	}

	@Override
	public void reflect(Vectorf<?> v) {
		float dot = 2.0f * dot(v);

		set(
				getX() - dot * v.get(0),
				getY() - dot * v.get(1),
				getZ() - dot * v.get(2)
		   );
	}

	@Override
	public Vector3f normalize() {
		return setValue(1f);
	}

	@Override
	public Vector3f normalized() {
		return clone().normalize();
	}

	@Override
	public float getValue() {
		if (isValueDirty) recalculateValue();
		return value;
	}

	@Override
	public float getSquaredValue() {
		return x * x + y * y + z * z;
	}

	@Override
	public Vector3f setValue(float value) {
		multiply(value / getValue());
		this.value = value;
		isValueDirty = false;
		return this;
	}

	public Vector3f transform(Matrix4f m) {
		float x = this.x;
		float y = this.y;
		float z = this.z;

		return set(
				x * m.get(0, 0) + y * m.get(0, 1) + z * m.get(0, 2),
				x * m.get(1, 0) + y * m.get(1, 1) + z * m.get(1, 2),
				x * m.get(2, 0) + y * m.get(2, 1) + z * m.get(2, 2)
		          );

	}

	public Vector3f transform(Matrix4f m, float w) {
		float x = this.x;
		float y = this.y;
		float z = this.z;

		return set(
				x * m.get(0, 0) + y * m.get(0, 1) + z * m.get(0, 2) + w * m.get(0, 3),
				x * m.get(1, 0) + y * m.get(1, 1) + z * m.get(1, 2) + w * m.get(1, 3),
				x * m.get(2, 0) + y * m.get(2, 1) + z * m.get(2, 2) + w * m.get(2, 3)
				);

	}

	private void recalculateValue() {
		setValueCache((float) Math.sqrt(getSquaredValue()));
	}

	private void setValueCache(float value) {
		this.value = value;
		isValueDirty = false;
	}

	@Override
	public Vector3f clone() {
		return new Vector3f(x, y, z, value, isValueDirty);
	}

	@Override
	public String toString() {
		return "(" + x + "|" + y + "|" + z + ")";
	}

	@Override
	public boolean equals(Object o) {
		// generated by IntelliJ IDEA
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vector3f vector3f = (Vector3f) o;

		if (Float.compare(vector3f.x, x) != 0) return false;
		if (Float.compare(vector3f.y, y) != 0) return false;
		return Float.compare(vector3f.z, z) == 0;
	}

	@Override
	public int hashCode() {
		// generated by IntelliJ IDEA
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
		return result;
	}

}
