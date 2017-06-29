using UnityEngine;
using System.Collections;

public class BoxGameStartPose : MonoBehaviour {

	public GameObject handsUp;
	private BoxController boxController;
	private GestureListener gestureListener;
	
	
	void Start () {
		boxController = transform.GetComponent<BoxController>();
	}
	
	
	void Update () {
		// dont run Update() if there is no user
		KinectManager kinectManager = KinectManager.Instance;
		if ( (!kinectManager || !kinectManager.IsInitialized () || !kinectManager.IsUserDetected ())) {
			handsUp.SetActive(true);
			return;
		}
		else if(kinectManager.IsUserDetected ()){
			handsUp.SetActive(false);
			boxController.enabled = true;
		}
		else if(kinectManager.IsUserDetected () == false){
			handsUp.SetActive(true);
			boxController.enabled = false;
		}
	}

}
