using UnityEngine;
using System.Collections;

public class GameController : MonoBehaviour {
	public GameObject redApple;
	public GameObject banana;
	public GameObject peach;
	public GameObject lemon;
	public GameObject rock;


	public Vector3 spawnValues;
	public int fruitCount;
	public float spawnWait;
	public float startWait;
	public float waveWait;
	
	void Start(){
		StartCoroutine( SpawnWaves () );
	}

	IEnumerator SpawnWaves(){
		yield return new WaitForSeconds(startWait );
		while(true){
			for(int i=0 ; i<fruitCount ; i++){
				int selectFruit = Random.Range(0,5);
				Vector3 spawnPosition = new Vector3 (Random.Range (-spawnValues.x, spawnValues.x), spawnValues.y, spawnValues.z);
				Quaternion spawnRotation = Quaternion.identity;

				if(selectFruit >= 0 && selectFruit <1){ Instantiate (redApple,spawnPosition,spawnRotation); }
				else if(selectFruit >= 1 && selectFruit <2){ Instantiate (banana,spawnPosition,spawnRotation); }
				else if(selectFruit >= 2 && selectFruit <3){ Instantiate (peach,spawnPosition,spawnRotation); }
				else if(selectFruit >= 3 && selectFruit < 4){ Instantiate (lemon,spawnPosition,spawnRotation); }
				else if(selectFruit >= 4 && selectFruit < 5){ Instantiate (rock,spawnPosition,spawnRotation); }

				yield return new WaitForSeconds(spawnWait);
			}
			yield return new WaitForSeconds(waveWait);
		}
	}


}
