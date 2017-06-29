using UnityEngine;
using System.Collections;

public class GameControllerForMusicGame : MonoBehaviour {
	public GameObject[] gestures;
	public GameObject boxType1;
	public GameObject boxType2;
	public GameObject boxType3;
	
	public Vector3 spawnValuesForBox1;
	public Vector3 spawnValuesForBox2;
	public Vector3 spawnValuesForBox3;

	public Vector3 myRotation = new Vector3 (0,0,180);

	public int boxCount;
	public float spawnWait;
	public float startWait;
	public float waveWait;
	
	
	void Start(){
		StartCoroutine( SpawnWaves () );
		
	}
	
	IEnumerator SpawnWaves(){
		yield return new WaitForSeconds(startWait );
		while(true){
			for(int i=0 ; i<boxCount ; i++){
				int spawnPosition = Random.Range(0,3);
				int selectedGesture = Random.Range(0,8);
				Quaternion spawnRotation = Quaternion.identity;
				
				if(spawnPosition == 0){ 
					//boxType1.transform.Rotate(myRotation);
					Instantiate (gestures[selectedGesture],spawnValuesForBox1,spawnRotation); 
					boxType1.transform.Rotate(myRotation);
				}
				else if(spawnPosition == 1){ 
					Instantiate (gestures[selectedGesture],spawnValuesForBox2,spawnRotation); 
				}
				else if(spawnPosition == 2){ 
					Instantiate (gestures[selectedGesture],spawnValuesForBox3,spawnRotation); 
				}
				
				yield return new WaitForSeconds(spawnWait);
			}
			yield return new WaitForSeconds(waveWait);
		}
	}
	
	
}
