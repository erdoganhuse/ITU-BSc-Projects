using UnityEngine;
using System.Collections;

public class FruitPickerStartPose : MonoBehaviour {
	public GameObject handsUp;
	private GameController gameController;
	private GestureListener gestureListener;


	void Start () {
		gestureListener = Camera.main.GetComponent<GestureListener>();
		gameController = transform.GetComponent<GameController>();
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
			gameController.enabled = true;
		}
		else if(kinectManager.IsUserDetected () == false){
			handsUp.SetActive(true);
			gameController.enabled = false;
		}
	}
}
