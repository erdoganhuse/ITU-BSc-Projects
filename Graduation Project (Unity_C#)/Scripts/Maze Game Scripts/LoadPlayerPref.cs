using UnityEngine;
using System.Collections;

public class LoadPlayerPref : MonoBehaviour {
	private GameObject player;

	private string PlayerPosition_x = "PlayerPosition_x";
	private string PlayerPosition_y = "PlayerPosition_y";
	private string PlayerPosition_z = "PlayerPosition_z";

	private string PlayerRotation_x = "PlayerRotation_x";
	private string PlayerRotation_y = "PlayerRotation_x";
	private string PlayerRotation_z = "PlayerRotation_x";

	private string[] DoorForFruitGameNames = {"FruitGame1","FruitGame2","FruitGame3","FruitGame4","FruitGame5","FruitGame6"};
	public GameObject[] DoorForFruitGames;

	private string[] DoorForMusicGameNames = {"MusicGame1","MusicGame2","MusicGame3","MusicGame4","MusicGame5"};
	public GameObject[] DoorForMusicGames;

	private string[] DoorForBoxGameNames = {"BoxGame1","BoxGame2","BoxGame3","BoxGame4","BoxGame5","BoxGame6","BoxGame7"};
	public GameObject[] DoorForBoxGames;


	void Awake () {
		ReloadPlayerPrefs ();
	}

	public void ReloadPlayerPrefs(){
		player = GameObject.FindGameObjectWithTag ("Player");
		if( PlayerPrefs.HasKey( PlayerPosition_x) ){
			player.transform.position = new Vector3 ( PlayerPrefs.GetFloat(PlayerPosition_x),PlayerPrefs.GetFloat(PlayerPosition_y),PlayerPrefs.GetFloat(PlayerPosition_z) );
			player.transform.eulerAngles = new Vector3 ( PlayerPrefs.GetFloat(PlayerRotation_x),PlayerPrefs.GetFloat(PlayerRotation_y),PlayerPrefs.GetFloat(PlayerRotation_z) );
		}

		for(int i=0 ; i<DoorForFruitGameNames.Length ; i++){
			if( PlayerPrefs.HasKey( DoorForFruitGameNames[i]) ){
				if( PlayerPrefs.GetInt( DoorForFruitGameNames[i] ) == 1 ){
					DoorForBoxGames[i].SetActive(false);
				}
			}
		}

		for(int i=0 ; i<DoorForMusicGames.Length ; i++){
			if( PlayerPrefs.HasKey( DoorForMusicGameNames[i]) ){
				if( PlayerPrefs.GetInt( DoorForMusicGameNames[i] ) == 1 ){
					DoorForMusicGames[i].SetActive(false);
				}
			}
		}

		for(int i=0 ; i<DoorForBoxGames.Length ; i++){
			if( PlayerPrefs.HasKey( DoorForBoxGameNames[i]) ){
				if( PlayerPrefs.GetInt( DoorForBoxGameNames[i] ) == 1 ){
					DoorForBoxGames[i].SetActive(false);
				}
			}
		}
	}

	public void SavePlayerPrefs(){
		player = GameObject.FindGameObjectWithTag ("Player");
		PlayerPrefs.SetFloat ( PlayerPosition_x, player.transform.position.x );
		PlayerPrefs.SetFloat ( PlayerPosition_y, player.transform.position.y );
		PlayerPrefs.SetFloat ( PlayerPosition_z, player.transform.position.z );

		PlayerPrefs.SetFloat ( PlayerRotation_x, player.transform.rotation.x );
		PlayerPrefs.SetFloat ( PlayerRotation_y, player.transform.rotation.y );
		PlayerPrefs.SetFloat ( PlayerRotation_z, player.transform.rotation.z );
	}

}
