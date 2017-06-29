using UnityEngine;
using System.Collections;

public class MusicGameStartPose : MonoBehaviour {

	public GameObject handsUp;
	private GameControllerForMusicGame gameControllerForMusicGame;
	
	void Start () {
		gameControllerForMusicGame = transform.GetComponent<GameControllerForMusicGame>();
	}
	
	
	void Update () {
		// dont run Update() if there is no user
		KinectManager kinectManager = KinectManager.Instance;
		if ( (!kinectManager || !kinectManager.IsInitialized () || !kinectManager.IsUserDetected ())) {
			handsUp.SetActive(true);
			gameControllerForMusicGame.enabled = false;
			return;
		}
		else if(kinectManager.IsUserDetected ()){
			handsUp.SetActive(false);
			gameControllerForMusicGame.enabled = true;
		}
	}
}
