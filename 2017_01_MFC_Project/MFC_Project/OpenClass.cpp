#include "stdafx.h"
#include "OpenClass.h"


OpenClass::OpenClass(){}
OpenClass::~OpenClass(){}

bool OpenClass::addClass(Class& s)
{
	for (int index = 0; index < cData.GetSize(); index++)
		if (cData.GetAt(index).getCalSubj().getSubName() == s.getCalSubj().getSubName() ||
			cData.GetAt(index).getCalSubj().getSubName() == "")
		{
			Subject::cnt--;
			return false;
		}

	cData.Add(s);
	return true;
}

CArray<Class>& OpenClass::getData()
{
	return cData;
}

int OpenClass::searchClass(int calNum)
{
	for (int index = 0; index < cData.GetSize(); index++)
		if (cData.GetAt(index).getCalNum() == calNum)
			return index;

	return -1;
}

bool OpenClass::editClass(Class& tmpC)
{
	int idx = searchClass(tmpC.getCalNum());

	if (idx == -1)
		return false;

	cData.SetAt(idx, tmpC);

	return true;
}

bool OpenClass::delClass(int calNum)
{
	int idx = searchClass(calNum);

	if (idx == -1)
		return false;

	cData.RemoveAt(idx);
	return true;
}