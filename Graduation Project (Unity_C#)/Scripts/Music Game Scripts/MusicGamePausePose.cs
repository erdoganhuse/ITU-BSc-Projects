using UnityEngine;
using System.Collections;

public class MusicGamePausePose : MonoBehaviour {

	public GUIText testText;
	public GestureListener gestureListener;
	public GameObject presentationCube;
	private GameControllerForMusicGame gameControllerForMusicGame;

	private PresentationScriptForMusicGame presentationScriptForMusicGame;
	private bool IsHandsUpped = false;


	void Start () {
		gameControllerForMusicGame = transform.GetComponent<GameControllerForMusicGame>();
	}
	
	void Update () {
		if(gestureListener.IsHandsUp() || IsHandsUpped){
			IsHandsUpped = true;
			gameControllerForMusicGame.enabled = false;
			presentationCube.SetActive(true);
			presentationScriptForMusicGame = presentationCube.GetComponent<PresentationScriptForMusicGame>();
			//presentationScriptForMusicGame
			if(presentationScriptForMusicGame.swipeDownTex != -1){
				if(presentationScriptForMusicGame.swipeDownTex == 0){
					testText.text = "00000000000000000000000000000000000";
					IsHandsUpped = false;
					gameControllerForMusicGame.enabled = true;
					presentationCube.SetActive(false);
				}
				else if(presentationScriptForMusicGame.swipeDownTex == 1){
					Application.LoadLevel("MainMenuSceneSwipe");
				}
				presentationScriptForMusicGame.swipeDownTex = -1;
			}
		}
		else{

		}

	}
}

/*
foreach(KinectGestures.Gestures gesture in Player1Gestures)
{
	DetectGesture(UserId, gesture);
}
*/