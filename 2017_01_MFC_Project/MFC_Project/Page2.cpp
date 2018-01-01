// Page2.cpp : ���� �����Դϴ�.
//

#include "stdafx.h"
#include "MFC_Project.h"
#include "Page2.h"
#include "afxdialogex.h"
#include "Class.h"


// Page2 ��ȭ �����Դϴ�.
IMPLEMENT_DYNAMIC(Page2, CPropertyPage)

Page2::Page2()
	: CPropertyPage(IDD_DIALOG2)
	, myNumOfStu(_T(""))
	, myInfo(_T(""))
{

}

Page2::~Page2()
{
}

void Page2::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
	DDX_Text(pDX, IDC_EDIT2, myNumOfStu);
	DDX_Control(pDX, IDC_COMBO1, myDayOfWeek);
	DDX_Control(pDX, IDC_COMBO2, myTime);
	DDX_Control(pDX, IDC_COMBO3, mySeveralT);
	DDX_Control(pDX, IDC_LIST3, myListBox);
	DDX_Control(pDX, IDC_LIST2, myListCon);
	DDX_Text(pDX, IDC_EDIT4, myInfo);
}


BEGIN_MESSAGE_MAP(Page2, CPropertyPage)
	ON_BN_CLICKED(IDC_BUTTON1, &Page2::OnBnClickedButton1)
	ON_BN_CLICKED(IDC_BUTTON4, &Page2::OnBnClickedButton4)
	ON_BN_CLICKED(IDC_BUTTON5, &Page2::OnBnClickedButton5)
	ON_BN_CLICKED(IDC_BUTTON6, &Page2::OnBnClickedButton6)
	ON_NOTIFY(LVN_ITEMCHANGED, IDC_LIST2, &Page2::OnLvnItemchangedList2)
END_MESSAGE_MAP()


// Page2 �޽��� ó�����Դϴ�.

BOOL Page2::OnInitDialog()
{
	CPropertyPage::OnInitDialog();

	// TODO:  ���⿡ �߰� �ʱ�ȭ �۾��� �߰��մϴ�.
	char *str[5] = { "�����ڵ�", "�����", "��������", "�����ð�", "�����ü�" };		// column�� text�� ����� ���ڿ�
	int colW[5] = { 80, 80, 80, 80, 80 };																// column�� ���� ���̸� ������ ��
	LVCOLUMN cols;
	// cols���� "Format(FMT) : ����, SUB ITEM : ���� �׸�, TEXT : �ؽ�Ʈ, WIDTH : ���� ����"�� ������ ���� ���
	cols.mask = LVCF_FMT | LVCF_SUBITEM | LVCF_TEXT | LVCF_WIDTH;
	// ���� �ؽ�Ʈ�� ���� ���ķ� ����
	cols.fmt = LVCFMT_LEFT;
	for (int i = 0; i < 5; i++)
	{
		cols.pszText = str[i];				// column ���� ����
		cols.iSubItem = i;					// column �����׸��� ����
		cols.cx = colW[i];					// column�� ���� ���� ����
		myListCon.InsertColumn(i, &cols);	// column�� List�� �߰�
	}

	for (int index = 0; index < 5; index++)
		myDayOfWeek.AddString(dayOfWeek[index]);

	for (int index = 0; index < 9; index++)
		myTime.AddString(time[index]);

	for (int index = 0; index < 3; index++)
		mySeveralT.AddString(several[index]);

	listBox();

	return TRUE;  // return TRUE unless you set the focus to a control
				  // ����: OCX �Ӽ� �������� FALSE�� ��ȯ�ؾ� �մϴ�.
}

void Page2::OnLvnItemchangedList2(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMLISTVIEW pNMLV = reinterpret_cast<LPNMLISTVIEW>(pNMHDR);
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	UpdateData(true);

	POSITION pos = myListCon.GetFirstSelectedItemPosition();
	int nitem = myListCon.GetNextSelectedItem(pos);

	selectedIdx = ((CMFC_ProjectApp*)AfxGetApp())->oc.searchClass(_ttoi(myListCon.GetItemText(nitem, 0)));	// �����ڵ�
	if (selectedIdx == -1)
		return;
	CString tmp;
	tmp.Format("%d", selectedIdx);

	Class& data = ((CMFC_ProjectApp*)AfxGetApp())->oc.getData().GetAt(selectedIdx);

	myNumOfStu.Format("%d", data.getCalNumOfStu());

	for (int index = 0; index < 5; index++)
		if (data.getCalDayOfWeek() == dayOfWeek[index])
			myDayOfWeek.SetCurSel(index);
	for (int index = 0; index < 9; index++)
		if (data.getCalTime() == time[index])
			myTime.SetCurSel(index);

	for (int index = 0; index < 3; index++)
		if (data.getCalSeveralT() == several[index])
			mySeveralT.SetCurSel(index);

	CFile file;
	int size = 0;
	file.Open(data.getCalInfoPath(), CFile::modeRead);
	size = file.GetLength() + 1;
	char *buf = new char[size];
	memset(buf, '\0', size);	// buf�� Null�� �ʱ�ȭ
	file.Read(buf, size);
	myInfo.Format("%s", buf);

	delete[] buf;
	file.Close();
	UpdateData(false);

	*pResult = 0;
}

void Page2::OnBnClickedButton1()	// ���ΰ�ħ
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	init();
	listBox();
	listView();
}


void Page2::OnBnClickedButton4()	// �����ϱ�
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	UpdateData(true);

	if (myNumOfStu == "" || myDayOfWeek.GetCurSel() == -1 || myDayOfWeek.GetCurSel() == -1 || myDayOfWeek.GetCurSel() == -1 || myInfo == "")
	{
		MessageBox("��� ������ �Է����ּ���.\n");
		return;
	}

	CString str = "";
	int cnt = 0;
	myListBox.GetText(myListBox.GetCurSel(), str);
	CString calNum = str.Tokenize(".", cnt);				// ������ȣ

	int idx = ((CMFC_ProjectApp*)AfxGetApp())->ac.searchSubject(_ttoi(calNum));
	if (idx < 0)
	{
		MessageBox("���� ��ǰ�Դϴ�.\n");
		return;
	}

	Subject& s = ((CMFC_ProjectApp*)AfxGetApp())->ac.getData().GetAt(idx);

	s.setSubOpen(true);

	CString path = "", dayOfWeek = "", time = "", severalT = "";

	path.Format("C:\\test\\%d.txt", s.getSubNum());
	myDayOfWeek.GetLBText(myDayOfWeek.GetCurSel(), dayOfWeek);
	myTime.GetLBText(myTime.GetCurSel(), time);
	mySeveralT.GetLBText(mySeveralT.GetCurSel(), severalT);


	Class tmp(0, s, _ttoi(myNumOfStu), dayOfWeek, time, severalT, path);
	((CMFC_ProjectApp*)AfxGetApp())->oc.addClass(tmp);

	CFile file;
	file.Open(path, CFile::modeCreate | CFile::modeWrite);
	file.Write(myInfo, lstrlen(myInfo));
	file.Close();

	init();
	listBox();
	listView();
	UpdateData(false);
}


void Page2::OnBnClickedButton5()	// �����ϱ�
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	UpdateData(true);

	Class& data = ((CMFC_ProjectApp*)AfxGetApp())->oc.getData().GetAt(selectedIdx);
	data.setCalNumOfStu(_ttoi(myNumOfStu));
	
	CString dayOfWeek = "", time = "", severalT = "";
	myDayOfWeek.GetLBText(myDayOfWeek.GetCurSel(), dayOfWeek);
	myTime.GetLBText(myTime.GetCurSel(), time);
	mySeveralT.GetLBText(mySeveralT.GetCurSel(), severalT);

	data.setCalDayOfWeek(dayOfWeek);
	data.setCalTime(time);
	data.setCalSeveralT(severalT);

	CFile file;
	file.Open(data.getCalInfoPath(), CFile::modeCreate | CFile::modeWrite);
	file.Write(myInfo, lstrlen(myInfo));
	file.Close();

	init();
	listBox();
	listView();
	UpdateData(false);
}


void Page2::OnBnClickedButton6()	// �����ϱ�
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	UpdateData(true);

	CArray<Class>& data = ((CMFC_ProjectApp*)AfxGetApp())->oc.getData();
	data.GetAt(selectedIdx).getCalSubj().setSubOpen(false);

	::DeleteFile(data.GetAt(selectedIdx).getCalInfoPath());

	data.RemoveAt(selectedIdx);

	init();
	listBox();
	listView();
	UpdateData(false);
}

void Page2::listBox()
{
	myListBox.ResetContent();

	CArray<Subject> &data = ((CMFC_ProjectApp*)AfxGetApp())->ac.getData();
	CString str = "";
	for (int index = 0; index < data.GetSize(); index++)
	{
		Subject s = data.GetAt(index);
		if (s.getSubOpen() == true)
			continue;
		str.Format("%d. %s\n", s.getSubNum(), s.getSubName());
		myListBox.AddString(str);
	}
}

void Page2::listView()
{
	UpdateData(true);

	CArray<Class> &data = ((CMFC_ProjectApp*)AfxGetApp())->oc.getData();
	char buf[50] = { '\0' };
	LVITEM item;
	item.mask = LVIF_TEXT;

	myListCon.DeleteAllItems();

	for (int index = 0; index < data.GetSize(); index++)
	{
		item.iItem = index;

		item.iSubItem = 0;
		sprintf(buf, "%d", data.GetAt(index).getCalNum());
		item.pszText = buf;
		myListCon.InsertItem(&item);

		item.iSubItem = 1;
		sprintf(buf, "%s", data.GetAt(index).getCalSubj().getSubName());
		item.pszText = buf;
		myListCon.SetItem(&item);

		item.iSubItem = 2;
		sprintf(buf, "%s", data.GetAt(index).getCalDayOfWeek());
		item.pszText = buf;
		myListCon.SetItem(&item);

		item.iSubItem = 3;
		sprintf(buf, "%s", data.GetAt(index).getCalTime());
		item.pszText = buf;
		myListCon.SetItem(&item);

		item.iSubItem = 4;
		sprintf(buf, "%s", data.GetAt(index).getCalSeveralT());
		item.pszText = buf;
		myListCon.SetItem(&item);
	}
}

void Page2::init()
{
	myNumOfStu.Empty();
	myDayOfWeek.SetCurSel(-1);
	myTime.SetCurSel(-1);
	mySeveralT.SetCurSel(-1);
	myInfo.Empty();

	UpdateData(false);
}


#if 0
void CClasswork_170207_04Dlg::OnBnClickedButton1()
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	UpdateData(true);
	CFile file;
	int size = 0;
	file.Open(path, CFile::modeRead);
	size = file.GetLength() + 1;
	char *buf = new char[size];
	memset(buf, '\0', size);	// buf�� Null�� �ʱ�ȭ
	file.Read(buf, size);
	file.Close();
	content.Format("%s", buf);
	UpdateData(false);
	delete[] buf;
}


void CClasswork_170207_04Dlg::OnBnClickedButton2()
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	UpdateData(true);
	CFile file;
	file.Open(path, CFile::modeCreate | CFile::modeWrite);
	file.Write(content, lstrlen(content));
	file.Close();
	content.Empty();
	UpdateData(false);
}

#endif
