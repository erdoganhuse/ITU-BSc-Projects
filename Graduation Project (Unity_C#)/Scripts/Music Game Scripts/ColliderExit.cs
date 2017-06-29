using UnityEngine;
using System.Collections;

public class ColliderExit : MonoBehaviour {

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


	public GameObject playerExplosion;
	public AudioSource whoosh01;
	public AudioSource whoosh02;
	public AudioSource whoosh03;
	public GUIText scoreText; 
	private int score; 

	public GUIText testText1;
	public GUIText testText2;
	public GUIText testText3;
	public GUIText testText4;


	void Start(){
		score = 0;
		scoreText.text = "Score : " + score.ToString ();

		testText1.text = "";
		testText2.text = "";
		testText3.text = "";
		testText4.text = "";
	}

	void Update(){
		/*
		testText1.text = thetaGesture7_a.ToString ();
		testText2.text = thetaGesture7_b.ToString ();
		testText3.text = thetaGesture7_c.ToString ();
		 */

	}

	void AddScore(){
		score = score + 10;
		scoreText.text = "Score : " + score.ToString ();
	}	

	void OnTriggerStay(Collider other) {
		if ( other.transform.name == "Gesture1(Clone)" && ( Input.GetKeyDown(KeyCode.Keypad1) || IsGesture1Done() ) ) {
			AddScore();
			whoosh01.audio.Play();
			Destroy (other.gameObject);
			Instantiate(playerExplosion, other.transform.position, other.transform.rotation);
		}
		else if ( other.transform.name == "Gesture2(Clone)" && ( Input.GetKeyDown(KeyCode.Keypad2) || IsGesture2Done() ) ) {
			AddScore();
			whoosh02.audio.Play();
			Destroy (other.gameObject);
			Instantiate(playerExplosion, other.transform.position, other.transform.rotation);
		}
		else if ( other.transform.name == "Gesture3(Clone)" && ( Input.GetKeyDown(KeyCode.Keypad3) || IsGesture3Done() ) ) {
			AddScore();
			whoosh03.audio.Play();
			Destroy (other.gameObject);
			Instantiate(playerExplosion, other.transform.position, other.transform.rotation);
		}
		else if ( other.transform.name == "Gesture4(Clone)" && ( Input.GetKeyDown(KeyCode.Keypad4) || IsGesture4Done() ) ) {
			AddScore();
			whoosh03.audio.Play();
			Destroy (other.gameObject);
			Instantiate(playerExplosion, other.transform.position, other.transform.rotation);
		}
		else if ( other.transform.name == "Gesture5(Clone)" && ( Input.GetKeyDown(KeyCode.Keypad5) || IsGesture5Done() ) ) {
			AddScore();
			whoosh03.audio.Play();
			Destroy (other.gameObject);
			Instantiate(playerExplosion, other.transform.position, other.transform.rotation);
		}
		else if ( other.transform.name == "Gesture6(Clone)" && ( Input.GetKeyDown(KeyCode.Keypad6) || IsGesture6Done() ) ) {
			AddScore();
			whoosh03.audio.Play();
			Destroy (other.gameObject);
			Instantiate(playerExplosion, other.transform.position, other.transform.rotation);
		}
		else if ( other.transform.name == "Gesture7(Clone)" && ( Input.GetKeyDown(KeyCode.Keypad7) || IsGesture7Done() ) ) {
			AddScore();
			whoosh03.audio.Play();
			Destroy (other.gameObject);
			Instantiate(playerExplosion, other.transform.position, other.transform.rotation);
		}
		else if ( other.transform.name == "Gesture8(Clone)" && ( Input.GetKeyDown(KeyCode.Keypad8) || IsGesture8Done() ) ) {
			AddScore();
			whoosh03.audio.Play();
			Destroy (other.gameObject);
			Instantiate(playerExplosion, other.transform.position, other.transform.rotation);
		}
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

	void ControlGestures(){

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

	bool IsGesture1Done(){
		float thetaGesture1_a = FindAngleFromJoints (_HipRight, _KneeRight, _AnkleRight,"z","y"); 
		float thetaGesture1_b = FindAngleFromJoints (_HipLeft, _KneeLeft, _AnkleLeft,"z","y"); 
		float thetaGesture1_c = FindAngleFromJoints (_Spine, _ShoulderRight, _ElbowRight,"z","y"); 
		float thetaGesture1_d = FindAngleFromJoints (_Spine, _ShoulderLeft, _ElbowLeft,"z","y"); 

		if( (thetaGesture1_a > 10.0f) && (thetaGesture1_b > 7.0f) && (thetaGesture1_c > 45.0f) && (thetaGesture1_d > 45.0f) ){
			return true;
		}
		else{
			return false;
		}
	}

	bool IsGesture2Done(){
		float thetaGesture2_a = FindAngleFromJoints (_Spine, _ShoulderCenter, _Head,"y","x"); 
		float thetaGesture2_b = FindAngleFromJoints (_ShoulderLeft, _ElbowLeft, _WristLeft,"y","x"); 
		float thetaGesture2_c = FindAngleFromJoints (_ShoulderRight, _ElbowRight, _WristRight,"y","x"); 

		if( (thetaGesture2_a > 15.0f) && (thetaGesture2_b < -45.0f) && (thetaGesture2_c > 45.0f) ){
			return true;
		}
		else{
			return false;
		}
	}

	bool IsGesture3Done(){
		float thetaGesture3_a = FindAngleFromJoints (_Spine, _ShoulderCenter, _Head,"y","x"); 
		float thetaGesture3_b = FindAngleFromJoints (_ShoulderCenter, _ShoulderLeft, _ElbowLeft,"y","x"); 
		float thetaGesture3_c = FindAngleFromJoints (_ShoulderRight, _ElbowRight, _WristRight,"y","x"); 
		
		if( (thetaGesture3_a > 15.0f) && (thetaGesture3_b < -75.0f || thetaGesture3_b > 60.0f ) && (thetaGesture3_c > 45.0f) ){
			return true;
		}
		else{
			return false;
		}
	}

	bool IsGesture4Done(){
		float thetaGesture4_a = FindAngleFromJoints (_ShoulderCenter, _HipCenter, _KneeRight,"z","y"); 
		float thetaGesture4_b = FindAngleFromJoints (_Spine, _ShoulderCenter, _ElbowRight,"z","y"); 

		if( (thetaGesture4_a < -45.0f) && (thetaGesture4_b < -45.0f) ){
			return true;
		}
		else{
			return false;
		}
	}

	bool IsGesture5Done(){
		float thetaGesture5_a = FindAngleFromJoints (_HipRight, _KneeRight, _AnkleRight,"z","y"); 
		float thetaGesture5_b = FindAngleFromJoints (_KneeRight, _HipCenter, _KneeLeft,"z","y"); 
		
		if( (thetaGesture5_a < -5.0f) && (thetaGesture5_b > 30.0f) ){
			return true;
		}
		else{
			return false;
		}
	}
	
	bool IsGesture6Done(){
		float thetaGesture6_a = FindAngleFromJoints (_HipRight, _KneeRight, _AnkleRight,"z","y"); 
		float thetaGesture6_b = FindAngleFromJoints (_HipLeft, _KneeLeft, _AnkleLeft,"z","y"); 
		float thetaGesture6_c = FindAngleFromJoints (_ShoulderRight, _ElbowRight, _WristRight,"y","x"); 
		float thetaGesture6_d = FindAngleFromJoints (_ShoulderLeft, _ElbowLeft, _WristLeft,"y","x"); 
		
		if( (thetaGesture6_a < -45.0f) && (thetaGesture6_b < -45.0f) && (thetaGesture6_c < -45.0f) && (thetaGesture6_d < -45.0f) ){
			return true;
		}
		else{
			return false;
		}
	}
	
	bool IsGesture7Done(){
		float thetaGesture7_a = FindAngleFromJoints (_Spine, _ShoulderCenter, _Head,"y","x"); 
		float thetaGesture7_b = FindAngleFromJoints (_ShoulderCenter, _ShoulderLeft, _ElbowLeft,"y","x"); 
		float thetaGesture7_c = FindAngleFromJoints (_ShoulderRight, _ElbowRight, _WristRight,"y","x"); 
		
		if( (thetaGesture7_a > -5.0f || thetaGesture7_a < 5.0f) && (thetaGesture7_b > 45.0f) && (thetaGesture7_c > 45.0f) ){
			return true;
		}
		else{
			return false;
		}
	}
	
	bool IsGesture8Done(){
		float thetaGesture8_a = FindAngleFromJoints (_Spine, _ShoulderCenter, _Head,"y","x"); 
		float thetaGesture8_b = FindAngleFromJoints (_ShoulderRight, _ElbowRight, _WristRight,"y","x"); 
		float thetaGesture8_c = FindAngleFromJoints (_ShoulderLeft, _ElbowLeft, _WristLeft,"y","x"); 

		if( (thetaGesture8_a > 15.0f) && (thetaGesture8_b < -30.0f) && (thetaGesture8_c < -30.0f) ){
			return true;
		}
		else{
			return false;
		}
	}
}
