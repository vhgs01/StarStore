package com.starstore.hugo.victor.starstore.utils;

import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Victor Hugo on 21/01/2018.
 */

// CLASSE QUE APLICA AS DEVIDAS MÁSCARAS
public class MaskHelper {
    public static class NumberCard implements TextWatcher {
        private EditText mEtCardNumber;

        public NumberCard(EditText etCardNumber) {
            this.mEtCardNumber = etCardNumber;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int textLength = mEtCardNumber.length();
            if (mEtCardNumber.length() > 4) {
                mEtCardNumber.removeTextChangedListener(this);
                if (textLength > 15) {
                    editable.replace(16, textLength, "");
                } else {
                    for (int i = 1; i <= (textLength / 4); i++) {
                        MarginSpan marginSPan = new MarginSpan(20);
                        editable.setSpan(marginSPan, (i * 4) - 1, (i * 4), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                mEtCardNumber.addTextChangedListener(this);
            }
        }
    }

    public static class ExpirationDateCard implements TextWatcher {

        private static final String INITIAL_MONTH_ADD_ON = "0";
        private static final String DEFAULT_MONTH = "01";
        private static final String SPACE = "/";
        private EditText mEditText;
        private int mLength;

        public ExpirationDateCard(EditText editText) {
            mEditText = editText;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mEditText.setError(null);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mLength = mEditText.getText().length();
        }

        @Override
        public void afterTextChanged(Editable s) {
            int currentLength = mEditText.getText().length();
            boolean ignoreBecauseIsDeleting = false;
            if (mLength > currentLength) {
                ignoreBecauseIsDeleting = true;
            }

            if (ignoreBecauseIsDeleting && s.toString().startsWith(SPACE)) {
                return;
            }

            if (s.length() == 1 && !isNumberChar(String.valueOf(s.charAt(0)))) {
                mEditText.setText(DEFAULT_MONTH + SPACE);
            } else if (s.length() == 1 && !isCharValidMonth(s.charAt(0))) {
                mEditText.setText(INITIAL_MONTH_ADD_ON + String.valueOf(s.charAt(0)) + SPACE);
            } else if (s.length() == 2 && String.valueOf(s.charAt(s.length() - 1)).equals(SPACE)) {
                mEditText.setText(INITIAL_MONTH_ADD_ON + String.valueOf(s));
            } else if (!ignoreBecauseIsDeleting &&
                    (s.length() == 2 && !String.valueOf(s.charAt(s.length() - 1)).equals(SPACE))) {
                mEditText.setText(mEditText.getText().toString() + SPACE);
            } else if (s.length() == 3 && !String.valueOf(s.charAt(s.length() - 1)).equals(SPACE) && !ignoreBecauseIsDeleting) {
                s.insert(2, SPACE);
                mEditText.setText(String.valueOf(s));
            } else if (s.length() > 3 && !isCharValidMonth(s.charAt(0))) {
                mEditText.setText(INITIAL_MONTH_ADD_ON + s);
            }

            if (!ignoreBecauseIsDeleting) {
                mEditText.setSelection(mEditText.getText().toString().length());
            }
        }

        private boolean isCharValidMonth(char charFromString) {
            int month = 0;
            if (Character.isDigit(charFromString)) {
                month = Integer.parseInt(String.valueOf(charFromString));
            }
            return month <= 1;
        }

        private boolean isNumberChar(String string) {
            return string.matches(".*\\d.*");
        }
    }
}
