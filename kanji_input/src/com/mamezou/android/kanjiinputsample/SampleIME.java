package com.mamezou.android.kanjiinputsample;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class SampleIME extends InputMethodService {

	private KeyboardView keyboardView;
	private MyKeyboard myKeyboard;
	private StringBuilder composing = new StringBuilder();

	@Override
	public AbstractInputMethodImpl onCreateInputMethodInterface() {
		return super.onCreateInputMethodInterface();
	}

	@Override
	public boolean onEvaluateInputViewShown() {
		return true;
	}

	@Override
	public boolean onEvaluateFullscreenMode() {
		return false;
	}

	@Override
	public boolean isExtractViewShown() {
		return true;
	}

	@Override
	public void onInitializeInterface() {
		myKeyboard = new MyKeyboard(this, R.xml.mykeyboard);
	}

	@Override
	public View onCreateInputView() {
		keyboardView = (KeyboardView) getLayoutInflater().inflate(
				R.layout.keyboard, null);

		keyboardView.setKeyboard(myKeyboard);
		keyboardView.setOnKeyboardActionListener(listener);
		return keyboardView;
	}

	@Override
	public void onStartInputView(EditorInfo info, boolean restarting) {
		super.onStartInputView(info, restarting);
	}

	private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

		public void onKey(int primaryCode, int[] keyCodes) {
			StringBuffer sb = new StringBuffer();
			sb.append((char) primaryCode);
			if (primaryCode == -1) {
				getCurrentInputConnection().commitText(composing,
						composing.length());
				composing.delete(0, composing.length());
				// 選択肢を非表示
				updateInputViewShown();
			} else {
				String kanji;
				if ('a' == (char) primaryCode) {
					kanji = "東京都新宿区";
				} else if ('b' == (char) primaryCode) {
					kanji = "東京都港区";
				} else if ('c' == (char) primaryCode) {
					kanji = "富士山";
				} else if ('d' == (char) primaryCode) {
					kanji = "浦和";
				} else {
					kanji = "";
				}
				composing.append(kanji);
				getCurrentInputConnection().setComposingText(composing,
						composing.length());
				// 選択肢を表示
				updateInputViewShown();
			}
		}

		public void onPress(int primaryCode) {
			Log.d("SampleIME", "onPress(" + primaryCode + ") called");
		}

		public void onRelease(int primaryCode) {
			Log.d("SampleIME", "onRelease(" + primaryCode + ") called");

		}

		public void onText(CharSequence text) {
			InputConnection connection = getCurrentInputConnection();
			connection.commitText(text, 1);
		}

		public void swipeDown() {
			Log.d("SampleIME", "swipeDown()");

		}

		public void swipeLeft() {
			Log.d("SampleIME", "swipeLeft()");

		}

		public void swipeRight() {
			Log.d("SampleIME", "swipeRight()");

		}

		public void swipeUp() {
			Log.d("SampleIME", "swipeUp()");

		}

	};

}
