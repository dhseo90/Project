#include "stdafx.h"
#include "Subject.h"

int Subject::cnt = 0;

Subject::Subject(){}
Subject::~Subject(){}

Subject::Subject(int subNum, CString subName)
{
	if (subNum == 0)
		this->subNum = ++cnt;
	else
		this->subNum = subNum;
	
	this->subName = subName;
	this->subOpen = false;
}

int Subject::getSubNum()
{
	return this->subNum;
}

void Subject::setSubNum(int subNum)
{
	this->subNum = subNum;
}

CString Subject::getSubName()
{
	return this->subName;
}

void Subject::setSubName(CString subName)
{
	this->subName = subName;
}

bool Subject::getSubOpen()
{
	return this->subOpen;
}

void Subject::setSubOpen(bool subOpen)
{
	this->subOpen = subOpen;
}