
// MFC_Project.h : PROJECT_NAME ���� ���α׷��� ���� �� ��� �����Դϴ�.
//

#pragma once

#ifndef __AFXWIN_H__
	#error "PCH�� ���� �� ������ �����ϱ� ���� 'stdafx.h'�� �����մϴ�."
#endif

#include "resource.h"		// �� ��ȣ�Դϴ�.
#include "AddClass.h"
#include "OpenClass.h"


// CMFC_ProjectApp:
// �� Ŭ������ ������ ���ؼ��� MFC_Project.cpp�� �����Ͻʽÿ�.
//

class CMFC_ProjectApp : public CWinApp
{
public:
	CMFC_ProjectApp();

// �������Դϴ�.
public:
	virtual BOOL InitInstance();

// �����Դϴ�.
	AddClass ac;
	OpenClass oc;

	DECLARE_MESSAGE_MAP()
};

extern CMFC_ProjectApp theApp;