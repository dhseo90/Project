// Page1.cpp : 구현 파일입니다.
//

#include "stdafx.h"
#include "MFC_Project.h"
#include "afxdialogex.h"

#include "Page1.h"
#include "Subject.h"
#include "AddClass.h"


// Page1 대화 상자입니다.

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


// Page1 메시지 처리기입니다.
BOOL Page1::OnInitDialog()
{
	CPropertyPage::OnInitDialog();

	// TODO:  여기에 추가 초기화 작업을 추가합니다.
	char *str[3] = { "과목코드", "과목명", "개설유무" };					// column에 text로 사용할 문자열
	int colW[3] = { 100, 100, 100 };										// column의 가로 길이를 지정한 것
	LVCOLUMN cols;
	// cols에서 "Format(FMT) : 정렬, SUB ITEM : 서브 항목, TEXT : 텍스트, WIDTH : 가로 길이"를 변경할 것을 명시
	cols.mask = LVCF_FMT | LVCF_SUBITEM | LVCF_TEXT | LVCF_WIDTH;
	// 먼저 텍스트를 왼쪽 정렬로 지정
	cols.fmt = LVCFMT_LEFT;
	for (int i = 0; i < 3; i++)
	{
		cols.pszText = str[i];				// column 내용 지정
		cols.iSubItem = i;					// column 서브항목의 순서
		cols.cx = colW[i];					// column의 가로 길이 설정
		myListCon.InsertColumn(i, &cols);	// column을 List에 추가
	}

	listView();

	return TRUE;  // return TRUE unless you set the focus to a control
				  // 예외: OCX 속성 페이지는 FALSE를 반환해야 합니다.
}

void Page1::OnNMClickList1(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMITEMACTIVATE pNMItemActivate = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
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

void Page1::OnBnClickedButton1()		// 등록
{
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
	UpdateData(true);
	Subject s(0, mySubject);
	if (((CMFC_ProjectApp*)AfxGetApp())->ac.addSubject(s) == false)
		MessageBox("과목 등록에 실패했습니다.\n");

	init();
	listView();
}


void Page1::OnBnClickedButton2()		// 삭제
{
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
	UpdateData(true);

	((CMFC_ProjectApp*)AfxGetApp())->ac.delSubject(mySubNum);

	init();
	listView();
}


void Page1::OnBnClickedButton3()		// 새로고침
{
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
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