package com.android.collect.library.ui.main.fragment;

import android.os.Bundle;

import com.android.collect.library.R;
import com.android.collect.library.base.BaseFragment;

/**
 * 贷款产品
 * Created by anzai on 2017/7/11.
 */

public class LoanProductFragment extends BaseFragment {

    private static LoanProductFragment loanProductFragment;

    public static LoanProductFragment getInstance(Bundle args) {
        if (loanProductFragment == null) {
            synchronized (LoanProductFragment.class) {
                if (loanProductFragment == null) {
                    loanProductFragment = new LoanProductFragment();
                    if (args != null) {
                        loanProductFragment.setArguments(args);
                    }
                }
            }
        }
        return loanProductFragment;
    }

    @Override
    protected int getCurrentView() {
        return R.layout.fragment_loan_product;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setData() {

    }
}
