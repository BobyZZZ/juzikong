package com.bb.kanjuzi.mvp.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.bean.SearchHistory;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.utils.ResUtils;
import com.bb.kanjuzi.view.FlowLayout;
import com.bb.kanjuzi.mvp.contract.SearchFragmentContract;
import com.bb.kanjuzi.mvp.presenter.SearchFragmentPresenter;
import com.bb.kanjuzi.mvp.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Boby on 2019/6/20.
 */

public class SearchFragment extends BaseFragment<SearchFragmentPresenter> implements SearchFragmentContract.View {
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_clear)
    TextView mClean;
    @BindView(R.id.ll_trip)
    FlowLayout mFlTrips;
    @BindView(R.id.ll_history)
    FlowLayout mFlHistorys;
    @BindView(R.id.root_layout)
    View mRoot;
    @BindView(R.id.tv_trip)
    TextView mTvTrip;
    @BindView(R.id.tv_history)
    TextView mTvHistory;
    String[] trips = new String[]{"三体", "爆裂鼓手", "魔方游戏", "海贼王", "两小无猜", "海上钢琴师"};
    private List<SearchHistory> mHistories;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public SearchFragmentPresenter createPresenter() {
        return new SearchFragmentPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View view) {
        mFlHistorys.setMaxLinesCount(2);
    }

    @Override
    protected void initListener() {
        mEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEtSearch.setHint(null);
                } else {
                    mEtSearch.setHint(R.string.search_text);
                }
            }
        });
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {//手指抬起时会触发两次，down 和up分别一次
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //按下回车抬起的时候搜索
                        if (!TextUtils.isEmpty(getKey())) {
                            mPresenter.record(getKey());
//                            SearchResultActivity.startSearchResultActivity(SearchFragment.this.getContext(), getKey());
                            SearchResultActivity.startSearchResultActivityForResult(SearchFragment.this, getKey());
                        } else {
                            Toast.makeText(SearchFragment.this.getContext(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });
        mClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearch.clearFocus();
                mPresenter.cleanHistory();
            }
        });
        mFlHistorys.setOnTagClickedListener(new FlowLayout.OnTagClickedListener() {
            @Override
            public void onClick(int index) {
                mPresenter.record(mHistories.get(index).getName());
                SearchResultActivity.startSearchResultActivityForResult(SearchFragment.this, mHistories.get(index).getName());
            }
        });
        mFlTrips.setOnTagClickedListener(new FlowLayout.OnTagClickedListener() {
            @Override
            public void onClick(int index) {
                mPresenter.record(trips[index]);
                SearchResultActivity.startSearchResultActivityForResult(SearchFragment.this, trips[index]);
            }
        });
    }

    @Override
    protected void process() {
        mPresenter.getHistory();
        ArrayList<SearchHistory> others = new ArrayList<>();
        for (int i = 0; i < trips.length; i++) {
            SearchHistory other = new SearchHistory(trips[i], trips[i]);
            others.add(other);
        }
        mFlTrips.setTextViews(others);
    }

    @Override
    public String getKey() {
        return mEtSearch.getText().toString();
    }

    @Override
    public void onLoadHistory(List<SearchHistory> histories) {
        mHistories = histories;
        if (histories == null) {
            mFlHistorys.removeAll();
        } else {
            mFlHistorys.setTextViews(histories);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.getHistory();
    }

    @Override
    protected void initTheme(int themeId) {
        switch (themeId) {
            case Constant.THEME_DAY:
                //日间模式
                mRoot.setBackgroundColor(ResUtils.getColor(R.color.white));
                mEtSearch.setBackgroundResource(R.drawable.selector_et_search);
                mEtSearch.setTextColor(ResUtils.getColor(R.color.black));
                mEtSearch.setHintTextColor(ResUtils.getColor(R.color.black));
                mTvTrip.setTextColor(ResUtils.getColor(R.color.black));
                mTvHistory.setTextColor(ResUtils.getColor(R.color.black));
                mClean.setTextColor(ResUtils.getColor(R.color.black));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mRoot.setBackgroundColor(ResUtils.getColor(R.color.background_night));
                mEtSearch.setBackgroundResource(R.drawable.selector_et_search_night);
                mEtSearch.setTextColor(ResUtils.getColor(R.color.white));
                mEtSearch.setHintTextColor(ResUtils.getColor(R.color.white));
                mTvTrip.setTextColor(ResUtils.getColor(R.color.white));
                mTvHistory.setTextColor(ResUtils.getColor(R.color.white));
                mClean.setTextColor(ResUtils.getColor(R.color.white));
                break;
        }
    }
}
