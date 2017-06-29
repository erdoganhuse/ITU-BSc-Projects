using UnityEngine;
using System.Collections;

public class FruitPickerPausePose : MonoBehaviour {

	public GUIText testText;
	public GestureListener gestureListener;
	public GameObject presentationCube;
	private GameController gameController;

	private PresentationScriptForFruitPicker presentationScriptForFruitPicker;

	private bool IsHandsUpped = false;
	
	
	void Start () {
		gameController = transform.GetComponent<GameController>();
	}
	
	void Update () {
		if(gestureListener.IsHandsUp() || IsHandsUpped){
			PauseGame();

			IsHandsUpped = true;
			gameController.enabled = false;
			presentationCube.SetActive(true);
			presentationScriptForFruitPicker = presentationCube.GetComponent<PresentationScriptForFruitPicker>();
			//presentationScriptForMusicGame
			if(presentationScriptForFruitPicker.swipeDownTex != -1){
				print(presentationScriptForFruitPicker.swipeDownTex );
				if(presentationScriptForFruitPicker.swipeDownTex == 0){
					PlayGame();
					IsHandsUpped = false;
					gameController.enabled = true;
					presentationCube.SetActive(false);
				}
				else if(presentationScriptForFruitPicker.swipeDownTex == 1){
					Application.LoadLevel("MainMenuSceneSwipe");
				}
				presentationScriptForFruitPicker.swipeDownTex = -1;
			}
		}
		else{
			PlayGame();
		}
	}

	void PauseGame(){
		GameObject basket = GameObject.FindGameObjectWithTag ("Player");
		basket.GetComponent<PlayerController> ().enabled = false;
	}


	void PlayGame(){
		GameObject basket = GameObject.FindGameObjectWithTag ("Player");
		basket.GetComponent<PlayerController> ().enabled = true;
	}
}
