// Page1.cpp : ���� �����Դϴ�.
//

#include "stdafx.h"
#include "MFC_Project.h"
#include "afxdialogex.h"

#include "Page1.h"
#include "Subject.h"
#include "AddClass.h"


// Page1 ��ȭ �����Դϴ�.

IMPLEMENT_DYNAMIC(Page1, CPropertyPage)

Page1::Page1()
	: CPropertyPage(IDD_DIALOG1)
	, mySubject(_T(""))
	, mySubNum(0)
	, mySubOpTF(_T(""))
{
}

Page1::~Page1()
{
}

void Page1::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
	DDX_Text(pDX, IDC_EDIT1, mySubject);
	DDX_Control(pDX, IDC_LIST1, myListCon);
	DDX_Text(pDX, IDC_EDIT2, mySubNum);
	DDX_Text(pDX, IDC_EDIT3, mySubOpTF);
}


BEGIN_MESSAGE_MAP(Page1, CPropertyPage)
	ON_BN_CLICKED(IDC_BUTTON1, &Page1::OnBnClickedButton1)
	ON_BN_CLICKED(IDC_BUTTON2, &Page1::OnBnClickedButton2)
	ON_BN_CLICKED(IDC_BUTTON3, &Page1::OnBnClickedButton3)
	ON_NOTIFY(NM_CLICK, IDC_LIST1, &Page1::OnNMClickList1)
END_MESSAGE_MAP()


// Page1 �޽��� ó�����Դϴ�.
BOOL Page1::OnInitDialog()
{
	CPropertyPage::OnInitDialog();

	// TODO:  ���⿡ �߰� �ʱ�ȭ �۾��� �߰��մϴ�.
	char *str[3] = { "�����ڵ�", "�����", "��������" };					// column�� text�� ����� ���ڿ�
	int colW[3] = { 100, 100, 100 };										// column�� ���� ���̸� ������ ��
	LVCOLUMN cols;
	// cols���� "Format(FMT) : ����, SUB ITEM : ���� �׸�, TEXT : �ؽ�Ʈ, WIDTH : ���� ����"�� ������ ���� ���
	cols.mask = LVCF_FMT | LVCF_SUBITEM | LVCF_TEXT | LVCF_WIDTH;
	// ���� �ؽ�Ʈ�� ���� ���ķ� ����
	cols.fmt = LVCFMT_LEFT;
	for (int i = 0; i < 3; i++)
	{
		cols.pszText = str[i];				// column ���� ����
		cols.iSubItem = i;					// column �����׸��� ����
		cols.cx = colW[i];					// column�� ���� ���� ����
		myListCon.InsertColumn(i, &cols);	// column�� List�� �߰�
	}

	listView();

	return TRUE;  // return TRUE unless you set the focus to a control
				  // ����: OCX �Ӽ� �������� FALSE�� ��ȯ�ؾ� �մϴ�.
}

void Page1::OnNMClickList1(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMITEMACTIVATE pNMItemActivate = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	POSITION pos = myListCon.GetFirstSelectedItemPosition();
	int nitem = myListCon.GetNextSelectedItem(pos);

	mySubNum = _ttoi(myListCon.GetItemText(nitem, 0));
	mySubject = myListCon.GetItemText(nitem, 1);
	if (_ttoi(myListCon.GetItemText(nitem, 2)))
		mySubOpTF = "True";
	else
		mySubOpTF = "False";

	UpdateData(false);
	*pResult = 0;
}

void Page1::OnBnClickedButton1()		// ���
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	UpdateData(true);
	Subject s(0, mySubject);
	if (((CMFC_ProjectApp*)AfxGetApp())->ac.addSubject(s) == false)
		MessageBox("���� ��Ͽ� �����߽��ϴ�.\n");

	init();
	listView();
}


void Page1::OnBnClickedButton2()		// ����
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	UpdateData(true);

	((CMFC_ProjectApp*)AfxGetApp())->ac.delSubject(mySubNum);

	init();
	listView();
}


void Page1::OnBnClickedButton3()		// ���ΰ�ħ
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	init();
	listView();
}

void Page1::listView()
{
	UpdateData(true);

	CArray<Subject> &data = ((CMFC_ProjectApp*)AfxGetApp())->ac.getData();
	char buf[50] = { '\0' };
	LVITEM item;
	item.mask = LVIF_TEXT;

	myListCon.DeleteAllItems();
	for (int index = 0; index < data.GetSize(); index++)
	{
		item.iItem = index;

		item.iSubItem = 0;
		sprintf(buf, "%d", data.GetAt(index).getSubNum());
		item.pszText = buf;
		myListCon.InsertItem(&item);

		item.iSubItem = 1;
		sprintf(buf, "%s", data.GetAt(index).getSubName());
		item.pszText = buf;
		myListCon.SetItem(&item);

		item.iSubItem = 2;
		if (data.GetAt(index).getSubOpen())
			item.pszText = "True";
		else
			item.pszText = "False";
		myListCon.SetItem(&item);
	}
}

void Page1::init()
{
	mySubject = "";
	mySubNum = 0;
	mySubOpTF = "";
	UpdateData(false);
}