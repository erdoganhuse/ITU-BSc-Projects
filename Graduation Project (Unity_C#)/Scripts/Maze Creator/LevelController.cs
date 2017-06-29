using UnityEngine;
using System.Collections;

public class LevelController : MonoBehaviour {

	public int mazeWidth;
	public int mazeHeight;
	public GameObject way;
	public GameObject wall;

	private class Cell {
		public bool left;
		public bool right;
		public bool up;
		public bool down;
		public bool visited;
		public Cell () {
			left = false;
			right = false;
			up = false;
			down = false;
			visited = false;
		}
	}

	private Cell [,] _maze;

	// Use this for initialization
	void Start () {
		_maze = new Cell[mazeHeight, mazeWidth];
		for (int i=0; i< mazeHeight; i++)
			for (int j=0; j<mazeWidth; j++)
				_maze [i, j] = new Cell ();
		int startingX = Mathf.RoundToInt (Random.value * (mazeWidth - 1));
		int startingY = Mathf.RoundToInt (Random.value * (mazeHeight - 1));
		CreateMaze (startingX, startingY);
		DrawMaze ();
	}

	void CreateMaze (int x, int y) {
		_maze [y, x].visited = true;
		int counter = 0;
		if (y+1 < mazeHeight)
			if (_maze [y+1, x].visited == false)
				counter ++;
		if (y-1 >= 0)
			if (_maze [y-1, x].visited == false)
				counter ++;
		if (x+1 < mazeWidth)
			if (_maze [y, x+1].visited == false)
				counter ++;
		if (x-1 >= 0)
			if (_maze [y, x-1].visited == false)
				counter ++;
		if (counter == 0)
			return;
		while (true) {
			float rand = Random.value;
			if (y+1 < mazeHeight) {
				if (rand < 0.25f && _maze[y+1, x].visited == false) {
					_maze [y, x].down = true;
					_maze [y+1, x].up = true;
					CreateMaze (x, y+1);
				}
			}
			if (y-1 >= 0) {
				if (rand > 0.25f && rand < 0.5f && _maze[y-1, x].visited == false) {
					_maze [y, x].up = true;
					_maze [y-1, x].down = true;
					CreateMaze (x, y-1);
				}
			}
			if (x+1 < mazeWidth) {
				if (rand > 0.5f && rand < 0.75f && _maze[y, x+1].visited == false) {
					_maze [y, x].right = true;
					_maze [y, x+1].left = true;
					CreateMaze (x+1, y);
				}
			}
			if (x-1 >= 0) {
				if (rand > 0.75f && rand < 1f && _maze[y, x-1].visited == false) {
					_maze [y, x].left = true;
					_maze [y, x-1].right = true;
					CreateMaze (x-1, y);
				}
			}
			counter = 0;
			if (y+1 < mazeHeight)
				if (_maze [y+1, x].visited == false)
					counter ++;
			if (y-1 >= 0)
				if (_maze [y-1, x].visited == false)
					counter ++;
			if (x+1 < mazeWidth)
				if (_maze [y, x+1].visited == false)
					counter ++;
			if (x-1 >= 0)
				if (_maze [y, x-1].visited == false)
					counter ++;
			if (counter == 0)
				break;
		}
	}

	void DrawMaze () {
		float baseWidth = 0 - 1f * (2 * mazeWidth + 1) / 2.0f + 0.5f;
		float baseHeight = 1f * (2 * mazeHeight + 1) / 2.0f - 0.5f;
		for (int i=0; i< 2 * mazeHeight + 1; i++) {
			for (int j=0; j< 2 * mazeWidth + 1; j++) {
				if (j % 2 == 1 && i == 2 * mazeHeight){
					GameObject go = Instantiate (wall) as GameObject;
					go.transform.position = new Vector3 (baseWidth + 1f * j, baseHeight - 1f * i, 0f);
				}
				else if (i % 2 == 1 && j == 2 * mazeWidth) {
					GameObject go = Instantiate (wall) as GameObject;
					go.transform.position = new Vector3 (baseWidth + 1f * j, baseHeight - 1f * i, 0f);
				} 
				else if (i % 2 == 1 && j % 2 == 1) {
					//GameObject go = Instantiate (way) as GameObject;
					//go.transform.position = new Vector3 (baseWidth + 1f * j, baseHeight - 1f * i, 0f);
					int indexX = (j-1)/2;
					int indexY = (i-1)/2;

					if (_maze[indexY, indexX].left == true) {
						//GameObject go2 = Instantiate (way) as GameObject;
						//go2.transform.position = new Vector3 (baseWidth + 1f * j - 1f, baseHeight - 1f * i, 0f);
					} 
					else {
						GameObject go2 = Instantiate (wall) as GameObject;
						go2.transform.position = new Vector3 (baseWidth + 1f * j - 1f, baseHeight - 1f * i, 0f);
					}

					if (_maze[indexY, indexX].up == true) {
						//GameObject go2 = Instantiate (way) as GameObject;
						//go2.transform.position = new Vector3 (baseWidth + 1f * j, baseHeight - 1f * i + 1f, 0f);
					} 
					else {
						GameObject go2 = Instantiate (wall) as GameObject;
						go2.transform.position = new Vector3 (baseWidth + 1f * j, baseHeight - 1f * i + 1f, 0f);
					}
				}
				else if (j % 2==0 && i % 2 == 0) {
					GameObject go = Instantiate (wall) as GameObject;
					go.transform.position = new Vector3 (baseWidth + 1f * j, baseHeight - 1f * i, 0f);
				}
			}
		}
	}

}
