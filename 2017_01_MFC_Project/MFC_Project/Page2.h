#pragma once
#include "afxwin.h"
#include "afxcmn.h"


// Page2 ��ȭ �����Դϴ�.

class Page2 : public CPropertyPage
{
	DECLARE_DYNAMIC(Page2)

public:
	Page2();
	virtual ~Page2();

// ��ȭ ���� �������Դϴ�.
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_DIALOG2 };
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV �����Դϴ�.

	DECLARE_MESSAGE_MAP()
public:
	int selectedIdx;
	char* dayOfWeek[5] = { "������", "ȭ����", "������", "�����", "�ݿ���" };
	char* time[9] = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00" };
	char* several[3] = { "1", "2", "3" };
	CString myNumOfStu;
	CComboBox myDayOfWeek;
	CComboBox myTime;
	CComboBox mySeveralT;
	CString myInfo;
	CListBox myListBox;
	CListCtrl myListCon;
	virtual BOOL OnInitDialog();
	afx_msg void OnBnClickedButton1();
	afx_msg void OnBnClickedButton4();
	afx_msg void OnBnClickedButton5();
	afx_msg void OnBnClickedButton6();
	void listView();
	void listBox();
	void init();
	afx_msg void OnLvnItemchangedList2(NMHDR *pNMHDR, LRESULT *pResult);
};
