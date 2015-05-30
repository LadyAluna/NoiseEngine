package de.nerogar.noise.render;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class InputHandler {

	public class KeyboardKeyEvent {
		public int key;
		public int scancode;
		public int action;
		public int mods;

		public boolean procssed = false;

		public KeyboardKeyEvent(int key, int scancode, int action, int mods) {
			this.key = key;
			this.scancode = scancode;
			this.action = action;
			this.mods = mods;
		}
	}

	public class MouseButtonEvent {
		public int button;
		public int action;
		public int mods;

		public boolean procssed = false;

		public MouseButtonEvent(int button, int action, int mods) {
			this.button = button;
			this.action = action;
			this.mods = mods;
		}
	}

	private long windowPointer;

	private double cursorXpos, cursorYpos;
	private double cursorXdelta, cursorYdelta;
	private double scrollDeltaY, scrollDeltaX;

	private StringBuilder inputText;

	private List<KeyboardKeyEvent> keyboardKeyEvents;
	private List<MouseButtonEvent> mouseButtonEvents;

	protected InputHandler(long windowPointer) {
		this.windowPointer = windowPointer;

		inputText = new StringBuilder();
		keyboardKeyEvents = new ArrayList<InputHandler.KeyboardKeyEvent>();
		mouseButtonEvents = new ArrayList<InputHandler.MouseButtonEvent>();
	}

	protected void setCursorPosition(double xpos, double ypos) {
		cursorXdelta = xpos - cursorXpos;
		cursorYdelta = ypos - cursorYpos;

		cursorXpos = xpos;
		cursorYpos = ypos;
	}

	//---[mouse]---
	public float getCursorPosX() {
		return (float) cursorXpos;
	}

	public float getCursorPosY() {
		return (float) cursorYpos;
	}

	public float getCursorDeltaX() {
		return (float) cursorXdelta;
	}

	public float getCursorDeltaY() {
		return (float) cursorYdelta;
	}

	public float getScrollDeltaX() {
		return (float) scrollDeltaX;
	}

	public float getScrollDeltaY() {
		return (float) scrollDeltaY;
	}

	protected void addMouseButtonEvent(int button, int action, int mods) {
		mouseButtonEvents.add(new MouseButtonEvent(button, action, mods));
	}

	protected void resetMouseButtonEvents() {
		mouseButtonEvents.clear();
	}

	public boolean isButtonDown(int button) {
		return glfwGetMouseButton(windowPointer, button) == GLFW_PRESS;
	}

	/**
	 * @return A list of all MouseButtonEvents. Set the processed flag to true, if that event was used.
	 */
	public List<MouseButtonEvent> getMouseButtonEvents() {
		List<MouseButtonEvent> events = new ArrayList<InputHandler.MouseButtonEvent>();

		for (MouseButtonEvent event : mouseButtonEvents) {
			if (!event.procssed) events.add(event);
		}

		return events;
	}

	protected void setScrollWheelDelta(double scrollDeltaX, double scrollDeltaY) {
		this.scrollDeltaX = scrollDeltaX;
		this.scrollDeltaY = scrollDeltaY;
	}

	//---[keyboard]---
	protected void addKeyboardKeyEvent(int key, int scancode, int action, int mods) {
		keyboardKeyEvents.add(new KeyboardKeyEvent(key, scancode, action, mods));
	}

	protected void resetKeyboardKeyEvents() {
		keyboardKeyEvents.clear();
	}

	/**
	 * @return A list of all KeyboardKeyEvent. Set the processed flag to true, if that event was used.
	 */
	public List<KeyboardKeyEvent> getKeyboardKeyEvents() {
		List<KeyboardKeyEvent> events = new ArrayList<InputHandler.KeyboardKeyEvent>();

		for (KeyboardKeyEvent event : keyboardKeyEvents) {
			if (!event.procssed) events.add(event);
		}

		return events;
	}

	public boolean getIsKeyDown(int key) {
		return glfwGetKey(windowPointer, key) == GLFW_PRESS;
	}

	protected void addInputTextCodepoint(int codePoint) {
		inputText.appendCodePoint(codePoint);
	}

	protected void resetInputText() {
		inputText = new StringBuilder();
	}

	public String getInputText() {
		String ret = inputText.toString();
		resetInputText();
		return ret;
	}

}