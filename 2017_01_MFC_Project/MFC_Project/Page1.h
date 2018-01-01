#pragma once
#include "afxcmn.h"


// Page1 대화 상자입니다.

class Page1 : public CPropertyPage
{
	DECLARE_DYNAMIC(Page1)

public:
	Page1();
	virtual ~Page1();

// 대화 상자 데이터입니다.
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_DIALOG1 };
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 지원입니다.

	DECLARE_MESSAGE_MAP()
public:
	CListCtrl myListCon;
	int mySubNum;
	CString mySubject;
	CString mySubOpTF;

	void init();
	void listView();
	virtual BOOL OnInitDialog();
	afx_msg void OnBnClickedButton1();
	afx_msg void OnBnClickedButton2();
	afx_msg void OnBnClickedButton3();
	afx_msg void OnNMClickList1(NMHDR *pNMHDR, LRESULT *pResult);
};
