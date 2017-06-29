using UnityEngine;
using System.Collections;
using System.IO;

public class TakingJointCoordinates : MonoBehaviour 
{
	// the joint we want to track
	public KinectWrapper.NuiSkeletonPositionIndex jointHandRight = KinectWrapper.NuiSkeletonPositionIndex.HandRight;
	public KinectWrapper.NuiSkeletonPositionIndex jointHandLeft = KinectWrapper.NuiSkeletonPositionIndex.HandLeft;
	
	// joint position at the moment, in Kinect coordinates
	public Vector3 handLeftPosition;
	public Vector3 handRightPosition;

	public float rightOrLeft;
	
	void Start () 
	{	
		rightOrLeft = 0;
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
					//if(handLeftPosition.)
					//string sLine = string.Format("{0:F3};{1};{2:F3};{3:F3};{4:F3}", Time.time, (int)joint, jointPos.x, jointPos.y, jointPos.z);
				}
			}
		}
		
	}
	
}
