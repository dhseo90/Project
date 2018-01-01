#pragma once
#include "Subject.h"

class Class
{
private:
	int calNum;				// 강의코드
	Subject calSubj;		// 과목정보(과목코드, 과목명, 개설유무)
	int calNumOfStu;		// 강의인원
	CString calDayOfWeek;	// 강의요일
	CString calTime;			// 강의시간
	CString calSeveralT;		// 강의시수
	CString calInfoPath;	// 강의계획 경로
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

