#pragma once
class Subject
{
private :
	int subNum;				// 과목코드
	CString subName;		// 과목명
	bool subOpen;			// 개설여부
public:
	static int cnt;
	Subject();
	~Subject();
	Subject(int subNum, CString subName);
	int getSubNum();
	void setSubNum(int subNum);
	CString getSubName();
	void setSubName(CString subName);
	bool getSubOpen();
	void setSubOpen(bool subOpen);
};

