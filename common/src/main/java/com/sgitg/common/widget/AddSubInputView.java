package com.sgitg.common.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.sgitg.common.R;
import com.sgitg.common.utils.StringUtils;
import com.yanzhenjie.nohttp.Logger;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/11/27 13:03
 */

public class AddSubInputView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private int inputValue = 0;
    private int mInputMax = Integer.MAX_VALUE;
    private int mInputMin = 0;
    private int mStep = 1;
    private int mPosition = 0;
    private OnWarnListener mOnWarnListener;
    private OnChangeValueListener mOnChangeValueListener;

    private EditText etInput;

    public AddSubInputView(Context context) {
        this(context, null);
    }

    public AddSubInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddSubInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //得到属性
        if (attrs != null) {
            LayoutInflater.from(context).inflate(R.layout.view_add_sub_input, this);
            ImageView icPlus = findViewById(R.id.ic_plus);
            ImageView icMinus = findViewById(R.id.ic_minus);
            etInput = findViewById(R.id.et_input);
            icPlus.setOnClickListener(this);
            icMinus.setOnClickListener(this);
            etInput.addTextChangedListener(this);
            etInput.setFocusable(true);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onNumberInput();
    }

    private void onNumberInput() {
        int count = getNumber();
        if (count < mInputMin) {
            inputValue = mInputMin;
            etInput.setText(String.valueOf(inputValue));
            warningForMin();
            if (mOnChangeValueListener != null) {
                mOnChangeValueListener.onChangeValue(inputValue, mPosition);
            }
            return;
        }

        if (count > mInputMax) {
            inputValue = mInputMax;
            etInput.setText(String.valueOf(inputValue));
            warningForMax();
            if (mOnChangeValueListener != null) {
                mOnChangeValueListener.onChangeValue(inputValue, mPosition);
            }
        } else {
            inputValue = count;
            if (mOnChangeValueListener != null) {
                mOnChangeValueListener.onChangeValue(inputValue, mPosition);
            }
        }
    }

    /**
     * 得到输入框的数量
     */
    public int getNumber() {

        if(StringUtils.isNullOrEmpty(etInput.getText().toString())){
            inputValue = mInputMin;
            if (mOnChangeValueListener != null) {
                mOnChangeValueListener.onChangeValue(inputValue, mPosition);
            }
            return mInputMin;
        }


        try {
            return Integer.parseInt(etInput.getText().toString());
        } catch (NumberFormatException e) {
            Logger.e(e.getMessage());
        }
        etInput.setText(String.valueOf(mInputMin));
        return mInputMin;
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ic_plus) {
            // 加
            if (inputValue < mInputMax) {
                inputValue += mStep;
                //正常添加
                etInput.setText(String.valueOf(inputValue));
            } else {
                //超过最大购买数
                warningForMax();
            }
        } else if (id == R.id.ic_minus) {
            // 减
            if (inputValue > mInputMin) {
                inputValue -= mStep;
                etInput.setText(String.valueOf(inputValue));
            } else {
                // 低于最小购买数
                warningForMin();
            }
        } else if (id == R.id.et_input) {
            // 输入框
            etInput.setSelection(etInput.getText().toString().length());
        }
    }

    public AddSubInputView setCurrentNumber(int currentNumber) {
        if (currentNumber < mInputMin) {
            inputValue = mInputMin;
        } else {
            inputValue = mInputMin;
        }
        etInput.setText(inputValue + "");
        return this;
    }

    public AddSubInputView setMax(int buyMax) {
        mInputMax = buyMax;
        return this;
    }

    public AddSubInputView setPosition(int position) {
        mPosition = position;
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

    public AddSubInputView setMin(int buyMin) {
        mInputMin = buyMin;
        return this;
    }

    public AddSubInputView setOnWarnListener(OnWarnListener onWarnListener) {
        mOnWarnListener = onWarnListener;
        return this;
    }

    public AddSubInputView setOnChangeValueListener(OnChangeValueListener onChangeValueListener) {
        mOnChangeValueListener = onChangeValueListener;
        return this;
    }

    public int getStep() {
        return mStep;
    }

    public AddSubInputView setStep(int step) {
        mStep = step;
        return this;
    }

    /**
     * 超过的最大数限制
     * Warning for buy max.
     */
    private void warningForMax() {
        if (mOnWarnListener != null) mOnWarnListener.onWarningForMax(mInputMax);
    }

    /**
     * 低于最小数
     * Warning for buy min.
     */
    private void warningForMin() {
        if (mOnWarnListener != null) mOnWarnListener.onWarningForMin(mInputMin);
    }


    public interface OnWarnListener {

        void onWarningForMax(double max);

        void onWarningForMin(double min);
    }

    public interface OnChangeValueListener {

        void onChangeValue(double value, int position);
    }
}