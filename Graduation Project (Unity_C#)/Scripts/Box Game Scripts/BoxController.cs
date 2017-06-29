using UnityEngine;
using System.Collections;

public class BoxController : MonoBehaviour {

	public float timer = 2f;
	private float counter = 0f;
	public GameObject coin;
	public GameObject bomb;
	public GameObject explosion;
	private int coinOrBomb;
	public int coinPercentage;

	public GameObject myAnimation1;
	public GameObject myAnimation2;

	public GUIText scoreText;
	private int score;

	public GameObject[] FrontBoxes;

	private int openedBox = 0;
	private bool isGestureDone = false;

	public bool headRight = false;
	public bool headleft = false;
	public bool armRight = false;
	public bool armLeft = false;
	public bool legRight = false;
	public bool legLeft = false;


	void Awake () {
		score = 0;
		scoreText.text = "Score : " + score; 
	}
	

	void Update () {
		if (counter == 0) {
			RandomOpener();
		}

		ControlBoxes ();

		counter = counter + Time.deltaTime;
		if( timer < counter){
			CloseAllBoxes();
			DestroyAllBombs();
			DestroyAllCoins();
			armLeft = armRight = headleft = headRight = legLeft = legRight = false;
			isGestureDone = false;
			counter = 0f;
		}
	}

	void ControlBoxes(){
		if( ( Input.GetKeyDown(KeyCode.Keypad4) || armLeft ) && openedBox == 2 && isGestureDone == false){ 
			WhenGestureDone();
			armLeft = true; 
		}	
		else if( ( Input.GetKeyDown(KeyCode.Keypad1) || armRight ) && openedBox == 3 && isGestureDone == false){ 
			WhenGestureDone();
			armRight = true; 
		}	
		else if( ( Input.GetKeyDown(KeyCode.Keypad5) || headleft ) && openedBox == 0 && isGestureDone == false){ 
			WhenGestureDone();
			headleft = true; 
		}	
		else if( ( Input.GetKeyDown(KeyCode.Keypad2) || headRight ) && openedBox == 1 && isGestureDone == false){ 
			WhenGestureDone();
			headRight = true; 
		}	
		else if( ( Input.GetKeyDown(KeyCode.Keypad6) || legLeft ) && openedBox == 4 && isGestureDone == false){ 
			WhenGestureDone();
			legLeft = true; 
		}	
		else if( ( Input.GetKeyDown(KeyCode.Keypad3) || legRight ) && openedBox == 5 && isGestureDone == false){ 
			WhenGestureDone();
			legRight = true;
		}
	}

	void WhenGestureDone(){
		isGestureDone = true;
		if(coinOrBomb == 0){ 
			AddScore(10); 
			myAnimation1.animation.Play();
			GameObject _coin = GameObject.FindGameObjectWithTag("Coin");
			Destroy(_coin);
		}
		else if(coinOrBomb == 1){ 
			AddScore(-20); 
			myAnimation2.animation.Play();
			GameObject _bomb = GameObject.FindGameObjectWithTag("Bomb");
			Destroy(_bomb);
		}
		Quaternion spawnRotation = Quaternion.identity;
		Instantiate (explosion,FrontBoxes[openedBox].transform.position,spawnRotation); 
	}

	void RandomOpener(){
		Quaternion spawnRotation = Quaternion.identity;
		int randNumber = Random.Range (0,6);
		while( randNumber == openedBox ){
			randNumber = Random.Range (0,6);
		}
		openedBox = randNumber;
		FrontBoxes[openedBox].renderer.enabled = false;
		int percentage  = Random.Range (0,100);
		if( coinPercentage > percentage){
			coinOrBomb = 0;
			Instantiate (coin,FrontBoxes[openedBox].transform.position,spawnRotation); 
		}
		else{
			coinOrBomb = 1;
			Instantiate (bomb,FrontBoxes[openedBox].transform.position,spawnRotation); 
		}
	}

	void CloseAllBoxes(){
		for(int i=0; i<FrontBoxes.Length ; i++ ){
			FrontBoxes[i].renderer.enabled = true;
		}
	}

	void DestroyAllCoins(){
		GameObject[] coins = GameObject.FindGameObjectsWithTag("Coin");
		for (int i=0; i<coins.Length; i++) {
			Destroy(coins[i]);
		}
	}

	void DestroyAllBombs(){
		GameObject[] bombs = GameObject.FindGameObjectsWithTag("Bomb");
		for (int i=0; i<bombs.Length; i++) {
			Destroy(bombs[i]);
		}
	}

	void AddScore( int value){
		score = score + value;
		scoreText.text = "Score : " + score; 
	}

}
