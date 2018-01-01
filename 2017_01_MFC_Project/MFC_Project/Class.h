#pragma once
#include "Subject.h"

class Class
{
private:
	int calNum;				// �����ڵ�
	Subject calSubj;		// ��������(�����ڵ�, �����, ��������)
	int calNumOfStu;		// �����ο�
	CString calDayOfWeek;	// ���ǿ���
	CString calTime;			// ���ǽð�
	CString calSeveralT;		// ���ǽü�
	CString calInfoPath;	// ���ǰ�ȹ ���
public:
	static int cnt;

	Class();
	~Class();
	Class(int calNum, Subject& calSubj, int calNumOfStu, CString calDayOfWeek, CString calTime, CString calSeveralT, CString calInfoPath);
	int getCalNum();
	void setCalNum(int calNum);
	Subject getCalSubj();
	int getCalNumOfStu();
	void setCalNumOfStu(int calNumOfStu);
	CString getCalDayOfWeek();
	void setCalDayOfWeek(CString calDayOfWeek);
	CString getCalTime();
	void setCalTime(CString calTime);
	CString getCalSeveralT();
	void setCalSeveralT(CString calSeveralT);
	CString getCalInfoPath();
	void setCalInfoPath(CString calInfoPath);
};

