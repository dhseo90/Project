#pragma once
#include "afxcmn.h"


// Page1 ��ȭ �����Դϴ�.

class Page1 : public CPropertyPage
{
	DECLARE_DYNAMIC(Page1)

public:
	Page1();
	virtual ~Page1();

// ��ȭ ���� �������Դϴ�.
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_DIALOG1 };
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV �����Դϴ�.

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
