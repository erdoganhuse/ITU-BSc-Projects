using UnityEngine;
using System.Collections;

public class GestureController : MonoBehaviour {
	
	// the joint we want to track
	private KinectWrapper.NuiSkeletonPositionIndex _AnkleLeft = KinectWrapper.NuiSkeletonPositionIndex.AnkleLeft;
	private KinectWrapper.NuiSkeletonPositionIndex _AnkleRight = KinectWrapper.NuiSkeletonPositionIndex.AnkleRight;
	private KinectWrapper.NuiSkeletonPositionIndex _ElbowLeft = KinectWrapper.NuiSkeletonPositionIndex.ElbowLeft;
	private KinectWrapper.NuiSkeletonPositionIndex _ElbowRight = KinectWrapper.NuiSkeletonPositionIndex.ElbowRight;
	private KinectWrapper.NuiSkeletonPositionIndex _FootLeft = KinectWrapper.NuiSkeletonPositionIndex.FootLeft;
	private KinectWrapper.NuiSkeletonPositionIndex _FootRight = KinectWrapper.NuiSkeletonPositionIndex.FootRight;
	private KinectWrapper.NuiSkeletonPositionIndex _HandLeft = KinectWrapper.NuiSkeletonPositionIndex.HandLeft;
	private KinectWrapper.NuiSkeletonPositionIndex _HandRight = KinectWrapper.NuiSkeletonPositionIndex.HandRight;
	private KinectWrapper.NuiSkeletonPositionIndex _Head = KinectWrapper.NuiSkeletonPositionIndex.Head;
	private KinectWrapper.NuiSkeletonPositionIndex _HipCenter = KinectWrapper.NuiSkeletonPositionIndex.HipCenter;
	private KinectWrapper.NuiSkeletonPositionIndex _HipLeft = KinectWrapper.NuiSkeletonPositionIndex.HipLeft;
	private KinectWrapper.NuiSkeletonPositionIndex _HipRight = KinectWrapper.NuiSkeletonPositionIndex.HipRight;
	private KinectWrapper.NuiSkeletonPositionIndex _KneeLeft = KinectWrapper.NuiSkeletonPositionIndex.KneeLeft;
	private KinectWrapper.NuiSkeletonPositionIndex _KneeRight = KinectWrapper.NuiSkeletonPositionIndex.KneeRight;
	private KinectWrapper.NuiSkeletonPositionIndex _ShoulderCenter = KinectWrapper.NuiSkeletonPositionIndex.ShoulderCenter;
	private KinectWrapper.NuiSkeletonPositionIndex _ShoulderLeft = KinectWrapper.NuiSkeletonPositionIndex.ShoulderLeft;
	private KinectWrapper.NuiSkeletonPositionIndex _ShoulderRight = KinectWrapper.NuiSkeletonPositionIndex.ShoulderRight;
	private KinectWrapper.NuiSkeletonPositionIndex _Spine = KinectWrapper.NuiSkeletonPositionIndex.Spine;
	private KinectWrapper.NuiSkeletonPositionIndex _WristLeft = KinectWrapper.NuiSkeletonPositionIndex.WristLeft;
	private KinectWrapper.NuiSkeletonPositionIndex _WristRight = KinectWrapper.NuiSkeletonPositionIndex.WristRight;
	
	// joint position at the moment, in Kinect coordinates
	private Vector3 MyAnkleLeft; 
	private Vector3 MyAnkleRight; 
	private Vector3 MyElbowLeft; 
	private Vector3 MyElbowRight; 
	private Vector3 MyFootLeft; 
	private Vector3 MyFootRight; 
	private Vector3 MyHandLeft; 
	private Vector3 MyHandRight; 
	private Vector3 MyHead; 
	private Vector3 MHipCenter; 
	private Vector3 MyHipLeft; 
	private Vector3 MyHipRight; 
	private Vector3 MyKneeLeft; 
	private Vector3 MyKneeRight; 
	private Vector3 MyShoulderCenter; 
	private Vector3 MShoulderLeft; 
	private Vector3 MyShoulderRight; 
	private Vector3 MySpine; 
	private Vector3 MyWristLeft; 
	private Vector3 MyWristRight; 

	private BoxController boxController;

	public GUIText testText;

	void Start(){
		boxController = GameObject.FindGameObjectWithTag ("GameController").GetComponent<BoxController>();
	}

	void FixedUpdate (){
		ControlGestures ();

		float theta;
		//theta = FindAngleFromJoints (_Spine, _ShoulderCenter, _Head,"y","x"); 
		//theta = FindAngleFromJoints (_Spine, _ShoulderCenter, _Head,"y","x"); 
		//theta = FindAngleFromJoints (_ShoulderCenter, _ShoulderLeft, _ElbowLeft,"y","x");  
		//theta = FindAngleFromJoints (_ShoulderCenter, _ShoulderRight, _ElbowRight,"y","x"); 
		//theta = FindAngleFromJoints (_HipCenter, _HipLeft, _KneeLeft,"z","y"); 
		theta = FindAngleFromJoints (_HipCenter, _HipRight, _KneeRight,"z","y"); 

		int t = (int)theta;
		testText.text = t.ToString();
	}

	void ControlGestures(){
		float thetaForHeadLeft = FindAngleFromJoints (_Spine, _ShoulderCenter, _Head,"y","x"); 
		float thetaForHeadRight = FindAngleFromJoints (_Spine, _ShoulderCenter, _Head,"y","x"); 
		float thetaForArmLeft = FindAngleFromJoints (_ShoulderCenter, _ShoulderLeft, _ElbowLeft,"y","x");  
		float thetaForArmRight = FindAngleFromJoints (_ShoulderCenter, _ShoulderRight, _ElbowRight,"y","x"); 
		float thetaForLegLeft = FindAngleFromJoints (_HipCenter, _HipLeft, _KneeLeft,"z","y"); 
		float thetaForLegRight = FindAngleFromJoints (_HipCenter, _HipRight, _KneeRight,"z","y"); 
		//print ("thetaForHeadLeft : " + thetaForHeadLeft);
		if( thetaForHeadLeft < -20.0f  ){
			//print("thetaForHeadLeft");
			boxController.headleft = true;
		}
		if( thetaForHeadRight > 15.0f  ){
			//print("thetaForHeadRight");
			boxController.headRight = true;
		}
		if( thetaForArmLeft > 15.0f  ){
			boxController.armLeft = true;
		}
		if( thetaForArmRight < -15.0f  ){
			boxController.armRight = true;
		}
		if( thetaForLegLeft < -30.0f  ){
			boxController.legLeft = true;
		}
		if( thetaForLegRight < -20.0f  ){
			boxController.legRight = true;
		}
	}

	public float FindAngleFromJoints(KinectWrapper.NuiSkeletonPositionIndex _Joint1, KinectWrapper.NuiSkeletonPositionIndex _Joint2,
	                                 KinectWrapper.NuiSkeletonPositionIndex _Joint3, string _Dimension1, string _Dimension2 ){
		KinectManager manager = KinectManager.Instance;
		Vector3 _JointPos1;
		Vector3 _JointPos2;
		Vector3 _JointPos3;

		if (IsJointTracked (_Joint1) == false) { return -1; }
		if (IsJointTracked (_Joint2) == false) { return -2; }
		if (IsJointTracked (_Joint3) == false) { return -3; }

		uint userId = manager.GetPlayer1ID();
		_JointPos1 = manager.GetJointPosition(userId, (int)_Joint1);
		_JointPos2 = manager.GetJointPosition(userId, (int)_Joint2);
		_JointPos3 = manager.GetJointPosition(userId, (int)_Joint3);

		float m1 = (_JointPos2.y - _JointPos1.y)/(_JointPos2.x - _JointPos1.x);
		float m2 = (_JointPos3.y - _JointPos2.y)/(_JointPos3.x - _JointPos2.x);

		if(_Dimension1 =="y" && _Dimension2 == "x"){
			m1 = (_JointPos2.y - _JointPos1.y)/(_JointPos2.x - _JointPos1.x);
			m2 = (_JointPos3.y - _JointPos2.y)/(_JointPos3.x - _JointPos2.x);
		}
		else if(_Dimension1 =="z" && _Dimension2 == "x"){
			m1 = (_JointPos2.z - _JointPos1.z)/(_JointPos2.x - _JointPos1.x);
			m2 = (_JointPos3.z - _JointPos2.z)/(_JointPos3.x - _JointPos2.x);
		}
		else if(_Dimension1 =="z" && _Dimension2 == "y"){
			m1 = (_JointPos2.z - _JointPos1.z)/(_JointPos2.y - _JointPos1.y);
			m2 = (_JointPos3.z - _JointPos2.z)/(_JointPos3.y - _JointPos2.y);
		}

		//print (" m1: " + Mathf.Atan(m1) +" m2: " + Mathf.Atan(m2) );

		float theta = ( Mathf.Atan( (m1-m2)/(1 + m1*m2)  ) ) * 180 / Mathf.PI;
		return theta;
	}

	bool IsJointTracked(KinectWrapper.NuiSkeletonPositionIndex _Joint){
		KinectManager manager = KinectManager.Instance;
		if(manager && manager.IsInitialized()){
			if( manager.IsUserDetected() ){
				uint userId = manager.GetPlayer1ID();
				if( manager.IsJointTracked(userId, (int)_Joint) ){
					return true;
				}
			}
		}
		return false;
	}
	
	bool IsAllJointsTracked(){
		if(IsJointTracked(_AnkleLeft) == false){	return false;	}
		else if(IsJointTracked(_AnkleRight) == false){	return false;	}
		else if(IsJointTracked(_ElbowLeft) == false){	return false;	}
		else if(IsJointTracked(_ElbowRight) == false){	return false;	}
		else if(IsJointTracked(_FootLeft) == false){	return false;	}
		else if(IsJointTracked(_FootRight) == false){	return false;	}
		else if(IsJointTracked(_HandLeft) == false){	return false;	}
		else if(IsJointTracked(_HandRight) == false){	return false;	}
		else if(IsJointTracked(_Head) == false){	return false;	}
		else if(IsJointTracked(_HipCenter) == false){	return false;	}
		else if(IsJointTracked(_HipLeft) == false){	return false;	}
		else if(IsJointTracked(_HipRight) == false){	return false;	}
		else if(IsJointTracked(_KneeLeft) == false){	return false;	}
		else if(IsJointTracked(_KneeRight) == false){	return false;	}
		else if(IsJointTracked(_ShoulderCenter) == false){	return false;	}
		else if(IsJointTracked(_ShoulderLeft) == false){	return false;	}
		else if(IsJointTracked(_ShoulderRight) == false){	return false;	}
		else if(IsJointTracked(_Spine) == false){	return false;	}
		else if(IsJointTracked(_WristLeft) == false){	return false;	}
		else if(IsJointTracked(_WristRight) == false){	return false;	}
		else{ return true; }
	}

}
