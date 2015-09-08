package de.nerogar.noise.render.deferredRenderer;

import de.nerogar.noise.debug.Profiler;
import de.nerogar.noise.util.Color;

public class DeferredRendererProfiler extends Profiler {

	public static final int OBJECT_COUNT			= 0;
	public static final int OBJECT_RENDER_COUNT		= 1;

	public static final int LIGHT_COUNT				= 2;
	public static final int LIGHT_RENDER_COUNT		= 3;

	public static final int EFFECT_COUNT			= 4;
	public static final int EFFECT_RENDER_COUNT		= 5;

	public DeferredRendererProfiler() {
		super("deferred renderer");

		registerProperty(OBJECT_COUNT,			new Color(1.0f, 0.1f, 0.0f, 0.0f),	"object count");
		registerProperty(OBJECT_RENDER_COUNT,	new Color(1.0f, 0.2f, 0.0f, 0.0f),	"object render count");

		registerProperty(LIGHT_COUNT,			new Color(1.0f, 1.0f, 0.2f, 0.0f),	"light count");
		registerProperty(LIGHT_RENDER_COUNT,	new Color(1.0f, 1.0f, 0.4f, 0.0f),	"light render count");

		registerProperty(EFFECT_COUNT,			new Color(0.0f, 1.0f, 0.0f, 0.0f),	"effect count");
		registerProperty(EFFECT_RENDER_COUNT,	new Color(0.3f, 1.0f, 0.3f, 0.0f),	"effect render count");
	}

	@Override
	public void reset() {
		super.reset();

		setValue(OBJECT_RENDER_COUNT, 0);

		setValue(LIGHT_COUNT, 0);
		setValue(LIGHT_RENDER_COUNT, 0);

		setValue(EFFECT_COUNT, 0);
		setValue(EFFECT_RENDER_COUNT, 0);
	}

}