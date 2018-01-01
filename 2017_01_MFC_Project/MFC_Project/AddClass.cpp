#include "stdafx.h"
#include "AddClass.h"


AddClass::AddClass(){}
AddClass::~AddClass(){}


bool AddClass::addSubject(Subject& s)
{
	for (int index = 0; index < data.GetSize(); index++)
		if (data.GetAt(index).getSubName() == s.getSubName() || data.GetAt(index).getSubName() == "")
		{
			Subject::cnt--;
			return false;
		}
	
	data.Add(s);
	return true;
}

CArray<Subject>& AddClass::getData()
{
	return data;
}

int AddClass::searchSubject(int subNum)
{
	for (int index = 0; index < data.GetSize(); index++)
		if (data.GetAt(index).getSubNum() == subNum)
			return index;

	return -1;
}

bool AddClass::delSubject(int subNum)
{
	int idx = searchSubject(subNum);

	if (idx == -1)
		return false;

	data.RemoveAt(idx);
	return true;
}