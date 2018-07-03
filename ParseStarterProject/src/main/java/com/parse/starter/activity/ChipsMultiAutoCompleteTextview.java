package com.parse.starter.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.support.v7.widget.AppCompatAutoCompleteTextView;

import com.parse.starter.R;

public class ChipsMultiAutoCompleteTextview extends AppCompatAutoCompleteTextView implements OnItemClickListener {

    private final String TAG = "ChipsMultiAutoCompleteTextview";

    // Constructor
    public ChipsMultiAutoCompleteTextview(Context context) {
        super(context);
        init(context);
    }

    // Constructor
    public ChipsMultiAutoCompleteTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // Constructor
    public ChipsMultiAutoCompleteTextview(Context context, AttributeSet attrs,
                                          int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    // set listeners for item click and text change
    public void init(Context context) {
        setOnItemClickListener(this);
        addTextChangedListener(textWather);
    }

    // TextWatcher, If user type any country name and press comma then following code will regenerate chips
    private TextWatcher textWather = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count >= 1) {
                if (s.charAt(start) == ',')
                    setChips(); // generate chips
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //This function has whole logic for chips generate
    public void setChips() {
        if (getText().toString().contains(",")) // check comman in string
        {

            SpannableStringBuilder ssb = new SpannableStringBuilder(getText());
            // split string wich comma
            String chips[] = getText().toString().trim().split(",");
            int x = 0;
            // loop will generate ImageSpan for every country name separated by comma
            for (String c : chips) {
                // inflate chips_edittext layout
                LayoutInflater lf = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                TextView textView = (TextView) lf.inflate(R.layout.item_autocomplete_dica, null);
                textView.setText(c); // set text

                // capture bitmapt of genreated textview
                int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                textView.measure(spec, spec);
                textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
                Bitmap b = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(b);
                canvas.translate(-textView.getScrollX(), -textView.getScrollY());
                textView.draw(canvas);
                textView.setDrawingCacheEnabled(true);
                Bitmap cacheBmp = textView.getDrawingCache();
                Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
                textView.destroyDrawingCache();  // destory drawable
            }
            // set chips span
            setText(ssb);
            // move cursor to last
            setSelection(getText().length());
        }


    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        setChips(); // call generate chips when user select any item from auto complete
    }






}