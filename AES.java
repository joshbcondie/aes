import java.util.Scanner;

public class AES {

	private static final int[][] sBox = new int[][] {
			{ 0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67,
					0x2b, 0xfe, 0xd7, 0xab, 0x76 },
			{ 0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2,
					0xaf, 0x9c, 0xa4, 0x72, 0xc0 },
			{ 0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5,
					0xf1, 0x71, 0xd8, 0x31, 0x15 },
			{ 0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80,
					0xe2, 0xeb, 0x27, 0xb2, 0x75 },
			{ 0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6,
					0xb3, 0x29, 0xe3, 0x2f, 0x84 },
			{ 0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe,
					0x39, 0x4a, 0x4c, 0x58, 0xcf },
			{ 0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02,
					0x7f, 0x50, 0x3c, 0x9f, 0xa8 },
			{ 0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda,
					0x21, 0x10, 0xff, 0xf3, 0xd2 },
			{ 0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e,
					0x3d, 0x64, 0x5d, 0x19, 0x73 },
			{ 0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8,
					0x14, 0xde, 0x5e, 0x0b, 0xdb },
			{ 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac,
					0x62, 0x91, 0x95, 0xe4, 0x79 },
			{ 0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4,
					0xea, 0x65, 0x7a, 0xae, 0x08 },
			{ 0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74,
					0x1f, 0x4b, 0xbd, 0x8b, 0x8a },
			{ 0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57,
					0xb9, 0x86, 0xc1, 0x1d, 0x9e },
			{ 0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87,
					0xe9, 0xce, 0x55, 0x28, 0xdf },
			{ 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d,
					0x0f, 0xb0, 0x54, 0xbb, 0x16 } };

	private static final int[][] invSBox = new int[][] {
			{ 0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3,
					0x9e, 0x81, 0xf3, 0xd7, 0xfb },
			{ 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43,
					0x44, 0xc4, 0xde, 0xe9, 0xcb },
			{ 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95,
					0x0b, 0x42, 0xfa, 0xc3, 0x4e },
			{ 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2,
					0x49, 0x6d, 0x8b, 0xd1, 0x25 },
			{ 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c,
					0xcc, 0x5d, 0x65, 0xb6, 0x92 },
			{ 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46,
					0x57, 0xa7, 0x8d, 0x9d, 0x84 },
			{ 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58,
					0x05, 0xb8, 0xb3, 0x45, 0x06 },
			{ 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd,
					0x03, 0x01, 0x13, 0x8a, 0x6b },
			{ 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf,
					0xce, 0xf0, 0xb4, 0xe6, 0x73 },
			{ 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37,
					0xe8, 0x1c, 0x75, 0xdf, 0x6e },
			{ 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62,
					0x0e, 0xaa, 0x18, 0xbe, 0x1b },
			{ 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0,
					0xfe, 0x78, 0xcd, 0x5a, 0xf4 },
			{ 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10,
					0x59, 0x27, 0x80, 0xec, 0x5f },
			{ 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a,
					0x9f, 0x93, 0xc9, 0x9c, 0xef },
			{ 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb,
					0x3c, 0x83, 0x53, 0x99, 0x61 },
			{ 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14,
					0x63, 0x55, 0x21, 0x0c, 0x7d } };

	private static final int[] Rcon = new int[] { 0x00000000, 0x01000000,
			0x02000000, 0x04000000, 0x08000000, 0x10000000, 0x20000000,
			0x40000000, 0x80000000, 0x1B000000, 0x36000000, 0x6C000000,
			0xD8000000, 0xAB000000, 0x4D000000, 0x9A000000, 0x2F000000,
			0x5E000000, 0xBC000000, 0x63000000, 0xC6000000, 0x97000000,
			0x35000000, 0x6A000000, 0xD4000000, 0xB3000000, 0x7D000000,
			0xFA000000, 0xEF000000, 0xC5000000, 0x91000000, 0x39000000,
			0x72000000, 0xE4000000, 0xD3000000, 0xBD000000, 0x61000000,
			0xC2000000, 0x9F000000, 0x25000000, 0x4A000000, 0x94000000,
			0x33000000, 0x66000000, 0xCC000000, 0x83000000, 0x1D000000,
			0x3A000000, 0x74000000, 0xE8000000, 0xCB000000, 0x8D000000 };

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("[E]ncode or [d]ecode: ");
		String method = scanner.nextLine();

		System.out.print("Input: ");
		String input = scanner.nextLine();

		System.out.print("Key: ");
		String key = scanner.nextLine();

		scanner.close();

		if (method.toUpperCase().startsWith("E"))
			bytesToString(cipher(stringToBytes(input), stringToBytes(key)));
		else
			bytesToString(invCipher(stringToBytes(input), stringToBytes(key)));
	}

	private static int[] stringToBytes(String s) {
		int[] bytes = new int[s.length() / 2];
		for (int i = 0; i < s.length(); i += 2)
			bytes[i / 2] = (value(s.charAt(i)) << 4) + value(s.charAt(i + 1));
		return bytes;
	}

	private static int value(char a) {
		if (a >= 'a' && a <= 'f')
			return a - 'a' + 10;
		else if (a >= 'A' && a <= 'F')
			return a - 'A' + 10;
		else
			return a - '0';
	}

	private static String bytesToString(int[] a) {
		String s = "";
		for (int i = 0; i < a.length; i++)
			s += String.format("%02x", a[i]);
		return s;
	}

	private static String wordsToString(int[] w, int round) {
		String s = "";
		for (int i = 0; i < 4; i++)
			s += String.format("%08x", w[i + round * 4]);
		return s;
	}

	private static String format(int num) {
		if (num >= 10)
			return num + "";
		return " " + num;
	}

	private static int[] cipher(int[] input, int[] key) {

		System.out.println("round[ 0].input    " + bytesToString(input));

		int[][] state = new int[4][4];
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				state[r][c] = input[r + 4 * c];

		int[] w = keyExpansion(key);
		int nr = key.length / 4 + 6;

		addRoundKey(state, w, 0);
		System.out.println("round[ 0].k_sch    " + wordsToString(w, 0));
		for (int round = 1; round <= nr - 1; round++) {
			System.out.println("round[" + format(round) + "].start    "
					+ stateToString(state));
			subBytes(state);
			System.out.println("round[" + format(round) + "].s_box    "
					+ stateToString(state));
			shiftRows(state);
			System.out.println("round[" + format(round) + "].s_row    "
					+ stateToString(state));
			mixColumns(state);
			System.out.println("round[" + format(round) + "].m_col    "
					+ stateToString(state));
			addRoundKey(state, w, round);
			System.out.println("round[" + format(round) + "].k_sch    "
					+ wordsToString(w, round));
		}

		System.out.println("round[" + format(nr) + "].start    "
				+ stateToString(state));
		subBytes(state);
		System.out.println("round[" + format(nr) + "].s_box    "
				+ stateToString(state));
		shiftRows(state);
		System.out.println("round[" + format(nr) + "].s_row    "
				+ stateToString(state));
		addRoundKey(state, w, nr);
		System.out.println("round[" + format(nr) + "].k_sch    "
				+ wordsToString(w, nr));

		int[] output = new int[16];
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				output[r + 4 * c] = state[r][c];

		System.out.println("round[" + format(nr) + "].output   "
				+ bytesToString(output));
		return output;
	}

	private static int[] invCipher(int[] input, int[] key) {

		System.out.println("round[ 0].iinput   " + bytesToString(input));

		int[][] state = new int[4][4];
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				state[r][c] = input[r + 4 * c];

		int[] w = keyExpansion(key);
		int nr = key.length / 4 + 6;

		addRoundKey(state, w, nr);
		System.out.println("round[ 0].ik_sch   " + wordsToString(w, nr));
		for (int round = nr - 1; round >= 1; round--) {
			System.out.println("round[" + format(nr - round) + "].istart   "
					+ stateToString(state));
			invShiftRows(state);
			System.out.println("round[" + format(nr - round) + "].is_row   "
					+ stateToString(state));
			invSubBytes(state);
			System.out.println("round[" + format(nr - round) + "].is_box   "
					+ stateToString(state));
			addRoundKey(state, w, round);
			System.out.println("round[" + format(nr - round) + "].ik_sch   "
					+ wordsToString(w, round));
			System.out.println("round[" + format(nr - round) + "].ik_add   "
					+ stateToString(state));
			invMixColumns(state);
		}

		System.out.println("round[" + format(nr) + "].istart   "
				+ stateToString(state));
		invShiftRows(state);
		System.out.println("round[" + format(nr) + "].is_row   "
				+ stateToString(state));
		invSubBytes(state);
		System.out.println("round[" + format(nr) + "].is_box   "
				+ stateToString(state));
		addRoundKey(state, w, 0);
		System.out.println("round[" + format(nr) + "].ik_sch   "
				+ wordsToString(w, 0));

		int[] output = new int[16];
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				output[r + 4 * c] = state[r][c];

		System.out.println("round[" + nr + "].ioutput  "
				+ bytesToString(output));
		return output;
	}

	private static void mixColumns(int[][] state) {
		int[][] statePrime = new int[4][4];
		int[] multiply2 = new int[4];
		int[] multiply3 = new int[4];

		for (int c = 0; c < 4; c++) {
			for (int r = 0; r < 4; r++) {
				multiply2[r] = xtime(state[r][c]);
				multiply3[r] = multiply2[r] ^ state[r][c];
			}

			statePrime[0][c] = multiply2[0] ^ multiply3[1] ^ state[2][c]
					^ state[3][c];
			statePrime[1][c] = state[0][c] ^ multiply2[1] ^ multiply3[2]
					^ state[3][c];
			statePrime[2][c] = state[0][c] ^ state[1][c] ^ multiply2[2]
					^ multiply3[3];
			statePrime[3][c] = multiply3[0] ^ state[1][c] ^ state[2][c]
					^ multiply2[3];
		}

		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				state[r][c] = statePrime[r][c];
	}

	private static void invMixColumns(int[][] state) {
		int[][] statePrime = new int[4][4];

		for (int c = 0; c < 4; c++) {
			statePrime[0][c] = ffMultiply(0x0e, state[0][c])
					^ ffMultiply(0x0b, state[1][c])
					^ ffMultiply(0x0d, state[2][c])
					^ ffMultiply(0x09, state[3][c]);
			statePrime[1][c] = ffMultiply(0x09, state[0][c])
					^ ffMultiply(0x0e, state[1][c])
					^ ffMultiply(0x0b, state[2][c])
					^ ffMultiply(0x0d, state[3][c]);
			statePrime[2][c] = ffMultiply(0x0d, state[0][c])
					^ ffMultiply(0x09, state[1][c])
					^ ffMultiply(0x0e, state[2][c])
					^ ffMultiply(0x0b, state[3][c]);
			statePrime[3][c] = ffMultiply(0x0b, state[0][c])
					^ ffMultiply(0x0d, state[1][c])
					^ ffMultiply(0x09, state[2][c])
					^ ffMultiply(0x0e, state[3][c]);
		}

		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				state[r][c] = statePrime[r][c];
	}

	private static void shiftRows(int[][] state) {
		int[][] statePrime = new int[4][4];
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				statePrime[r][c] = state[r][(c + r) % 4];

		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				state[r][c] = statePrime[r][c];
	}

	private static void invShiftRows(int[][] state) {
		int[][] statePrime = new int[4][4];
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				statePrime[r][(c + r) % 4] = state[r][c];

		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				state[r][c] = statePrime[r][c];
	}

	private static void subBytes(int[][] state) {
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				state[r][c] = sBox[state[r][c] >> 4][state[r][c] & 0x0f];
	}

	private static void invSubBytes(int[][] state) {
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				state[r][c] = invSBox[state[r][c] >> 4][state[r][c] & 0x0f];
	}

	private static void addRoundKey(int[][] state, int[] w, int round) {
		int[][] statePrime = new int[4][4];
		int l = round * 4;

		for (int c = 0; c < 4; c++) {
			statePrime[0][c] = state[0][c] ^ (w[l + c] >> 24) & 0xff;
			statePrime[1][c] = state[1][c] ^ (w[l + c] >> 16) & 0xff;
			statePrime[2][c] = state[2][c] ^ (w[l + c] >> 8) & 0xff;
			statePrime[3][c] = state[3][c] ^ w[l + c] & 0xff;
		}

		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				state[r][c] = statePrime[r][c];
	}

	private static int[] keyExpansion(int key[]) {
		int nk = key.length / 4;
		int nr = nk + 6;

		int[] w = new int[4 * (nr + 1)];
		int temp;

		for (int i = 0; i < nk; i++)
			w[i] = word(key[4 * i], key[4 * i + 1], key[4 * i + 2],
					key[4 * i + 3]);

		for (int i = nk; i < 4 * (nr + 1); i++) {
			temp = w[i - 1];
			if (i % nk == 0)
				temp = subWord(rotWord(temp)) ^ Rcon[i / nk];
			else if (nk > 6 && i % nk == 4)
				temp = subWord(temp);
			w[i] = w[i - nk] ^ temp;
		}

		return w;
	}

	private static int subWord(int a) {
		return (sBox[(a >> 28) & 0x0f][(a >> 24) & 0x0f] << 24)
				| (sBox[(a >> 20) & 0x0f][(a >> 16) & 0x0f] << 16)
				| (sBox[(a >> 12) & 0x0f][(a >> 8) & 0x0f] << 8)
				| sBox[(a >> 4) & 0x0f][a & 0x0f];
	}

	private static int rotWord(int a) {
		return (((a >> 16) & 0xff) << 24) | (((a >> 8) & 0xff) << 16)
				| ((a & 0xff) << 8) | ((a >> 24) & 0xff);
	}

	private static int word(int a, int b, int c, int d) {
		return (a << 24) | (b << 16) | (c << 8) | d;
	}

	private static int xtime(int a) {
		if ((a & 0x80) != 0)
			return ((a << 1) ^ 0x011b);
		return a << 1;
	}

	private static int ffMultiply(int a, int b) {
		int[] multiply = new int[8];
		multiply[0] = a;

		int exp = 0;
		while (1 << (exp + 1) <= b) {
			exp++;
			multiply[exp] = xtime(multiply[exp - 1]);
		}

		int sum = 0;
		while (exp >= 0) {
			if ((b & (1 << exp)) != 0)
				sum ^= multiply[exp];
			exp--;
		}
		return sum;
	}

	private static String stateToString(int[][] state) {
		String s = "";
		for (int c = 0; c < 4; c++)
			for (int r = 0; r < 4; r++)
				s += bytesToString(new int[] { state[r][c] });
		return s;
	}
}