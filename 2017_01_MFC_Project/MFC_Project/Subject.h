#pragma once
class Subject
{
private :
	int subNum;				// �����ڵ�
	CString subName;		// �����
	bool subOpen;			// ��������
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

