using UnityEngine;
using System.Collections;

public class PlayerController : MonoBehaviour {
	public float speed ;
	public float xMin, xMax ;
	public float tilt;

	// the joint we want to track
	public KinectWrapper.NuiSkeletonPositionIndex jointHandRight = KinectWrapper.NuiSkeletonPositionIndex.HandRight;
	public KinectWrapper.NuiSkeletonPositionIndex jointHandLeft = KinectWrapper.NuiSkeletonPositionIndex.HandLeft;
	
	// joint position at the moment, in Kinect coordinates
	public Vector3 handLeftPosition;
	public Vector3 handRightPosition;
	public float rightOrLeft;
	public float basketSpeed = 0.25f;
	public float changeMargin = 0.1f;

	void Start () 
	{	
		rightOrLeft = 0;
	}

	void FixedUpdate () {
		float x = Input.GetAxis ("Horizontal");
		//float y = Input.GetAxis ("Vertical");

		Vector3 movement = new Vector3 ( (basketSpeed)*rightOrLeft , 0.0f, 0.0f); //Kinect için kontroller
		//Vector3 movement = new Vector3 ( x , 0.0f, 0.0f); // Klavye için kontroller

		rigidbody.velocity = movement * speed;
		rigidbody.position = new Vector3 ( Mathf.Clamp(rigidbody.position.x, xMin, xMax), 0.0f, 0.0f);
	}
	
	void Update () 
	{	
		// get the joint position
		KinectManager manager = KinectManager.Instance;
		
		if(manager && manager.IsInitialized())
		{
			if(manager.IsUserDetected())
			{
				uint userId = manager.GetPlayer1ID();
				
				if(manager.IsJointTracked(userId, (int)jointHandLeft) && manager.IsJointTracked(userId, (int)jointHandRight))
				{
					// output the joint position for easy tracking
					Vector3 jointPos = manager.GetJointPosition(userId, (int)jointHandLeft);
					handLeftPosition = jointPos;
					jointPos = manager.GetJointPosition(userId, (int)jointHandRight);
					handRightPosition = jointPos;
					float difference = handRightPosition.y - handLeftPosition.y;
					if(   Mathf.Abs(difference) < changeMargin ){
						rightOrLeft = 0;
					}
					else if( changeMargin < difference ){
						rightOrLeft = 1;
					}
					else if( -changeMargin > difference ){
						rightOrLeft = -1;
					}
				}
			}
		}
		
	}	
}
