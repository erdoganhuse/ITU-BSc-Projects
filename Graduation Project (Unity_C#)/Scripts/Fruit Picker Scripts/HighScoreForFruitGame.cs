using UnityEngine;
using System.Collections;

public class HighScoreForFruitGame : MonoBehaviour {
	private string highScoreForFruitGame = "highScoreForFruitGame";

	public DestroyByContact destroyByContact;
	public GUIText highScoreText;

	void Start () {
		if( PlayerPrefs.HasKey( highScoreForFruitGame) == false ){
			PlayerPrefs.SetInt ( highScoreForFruitGame, 0 );	
		}
		else{
			highScoreText.text = "High Score : " + PlayerPrefs.GetInt(highScoreForFruitGame).ToString();
		}
	}
	
	void Update () {
		if( PlayerPrefs.HasKey( highScoreForFruitGame) ){
			if(PlayerPrefs.GetInt(highScoreForFruitGame) < destroyByContact.score  ){
				PlayerPrefs.SetInt ( highScoreForFruitGame, destroyByContact.score );	
				highScoreText.text = "High Score : " + destroyByContact.score.ToString();
			}
		}
	}
}
