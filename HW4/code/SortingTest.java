import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void DoBubbleSortRecursive(int[] value, int length) {
		// Bubble sort 를 재귀적으로 수행하기 위한 메소드
		if (length<2) {
			return;
		}
		int temp;
		for (int i=0; i<length-1; i++) {
			if (value[i] > value[i+1]) {
				temp = value[i];
				value[i] = value[i+1];
				value[i+1] = temp;
			}
		}
		DoBubbleSortRecursive(value, length-1);
	}

	private static int[] DoBubbleSort(int[] value)
	{
		DoBubbleSortRecursive(value, value.length);
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		int max;
		int temp;
		for (int i=value.length-1; i>0; i--) {
			max = 0;
			for (int j=1; j<=i; j++) {
				if (value[j] > value[max]) max = j;
			}
			if (max != i) {
				temp = value[i];
				value[i] = value[max];
				value[max] = temp;
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void percolateDown(int[] value, int index, int length) {
		// 배열 안 특정 원소를 percolate down 하는 메소드
		int child = 2 * index + 1;
		int right = 2 * index + 2;
		int temp;
		if (child < length) {
			if (right < length && value[child] < value[right]) {
				child = right;
			}
			if (value[index] < value[child]) {
				temp = value[index];
				value[index] = value[child];
				value[child] = temp;
				percolateDown(value, child, length);
			}
		}
	}

	private static void BuildHeap(int[] value) {
		// 주어진 배열을 heap 으로 만들기 위한 메소드
		for (int i=value.length/2-1; i>=0; i--) {
			percolateDown(value, i, value.length);
		}
	}

	private static int[] DoHeapSort(int[] value) {
		BuildHeap(value);
		int temp;
		for (int i=value.length-1; i>0; i--) {
			temp = value[i];
			value[i] = value[0];
			value[0] = temp;
			percolateDown(value, 0, i);
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void Merge(int[] A, int[] B) {
		// 두 배열을 merge 하는 메소드
	}
	
	private static void DoMergeSortRecursive(int[] A, int[] B) {
		// Merge sort 를 재귀적으로 수행하기 위한 메소드

	}
	
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort 를 구현하라.
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		return (value);
	}
}
