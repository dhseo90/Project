#pragma once
#include "afxdlgs.h"
#include "Page1.h"
#include "Page2.h"

class MySheet :
	public CPropertySheet
{
public:
	MySheet();
	~MySheet();

	Page1 myPage1;
	Page2 myPage2;
};

