# Thread

### 1. 프로세서와 스레드

##### 1.1 프로세서와 다중 프로세스

- '프로그램'이란 명령어의 집합

- 명령어가 실행되기 위해서는 주기억 장치(흔히 RAM)에 **'Loading'**되어야 함

- 이렇게 Loading 되어 실행 가능한 상태가 된 것을 **'프로세스'**라고 정의함

  ###### 1.1.1. 다중 프로세스

- 여러 작업을 조금씩 번갈아 가면서 수행하므로 해당 프로세스는 다소 느릴 뿐, 자신만 수행하는 것처럼 느끼게 되고, 전체적으로는 동시에 수행하는 것처럼 느낌

### 2. Thread

##### 1.1. 다중 프로세서를 지원하는 운영체제의 문제점

- 다중 프로세스를 사용해 작업을 번갈아 가면서 수행하는 경우, 프로세스 간 스위칭(switching)이 발생
- 프로세스 간 스위칭을 컨텍스트 스위칭(context swtiching)이라고 하는데, 이는 문맥, 즉 프로세스 영역에 변화가 일어남을 의미
- 스위칭이 발생할 때마다 현재 상태를 저장한 후, 다른 프로세스로 스위칭이 이뤄져야함
- 프로세스 차원에서 상태 정보를 저장하고 관리한다는 것은 다뤄야 할 정보 값이 많기 때문에 상당히 부담스러운 작업임
- 그래서 만들어진 것이 경량화된 프로세스, 즉 스레드!

##### 1.2. 스레드란

> 하나의 프로세스 내에서 나눠 작업하는 경량화된 프로세스, **Thread**.

- 스레드는 프로세스 내에서 나뉜 하나의 작업 단위
- 작업 단위란 실행 가능한 최소한의 단위를 의미하며, 실행을 위한 독립적 단위가 되기 위해선 스레드 내에 실행을 위한 기본 구성 요소인 '데이터(변수)' + '코드(함수)'로 이뤄져야 함
- 하나의 프로세스에 여러 개의 스레드가 존재할 수 있고, 스레드는 실행에 필요한 최소한의 데이터만을 갖고 자신이 속해 있는 프로세스의 실행 환경을 공유함

##### 1.3. 스레드 구현 방법

###### 1.3.1. 스레드로부터 직접 생성

- 기본 타입은 아래와 같음

```java
Thread thread = new Thread(Runnable target);
```

```java
// 1. Thread 객체 생성 시, 매개변수로 Runnable 객체를 넘겨줌
Runnable task = new Task();
Thread thread = new Thread(task);

// 1.1. 작업하고자 하는 객체가 다른 객체의 상속을 받아야 할 경우, Thread를 클래스로 처리하지 않고 Runnalbe를 implements 받음 
class Task implements Runnable {
  public void run(){
  }
}

// 2. 익명객체 활용 - 코드양이 줄어드는 효과
Thread thread = new Thread(new Runnable()){
  public void run(){
    // 스레드가 실행할 소스 코드 작성
  }
}

```

###### 1.3.2. 스레드 하위 클래스로부터 생성

- 작업 스레드가 실행할 작업을 Runnable로 만들지 않고, Thread의 하위 클래스로 작업 스레드를 정의하면서 작업 내용을 포함시킬 수도 있음

```java
// 1. 객체 직접 생성
public class WorkerThread extends Thread{
	@Override
	public void run(){
  		// 스레드가 실행할 코드;
	}
}
Thread thread = new WorkerThread());

// 2. 익명 객체 활용
Thread thread = new Thread(){
  public void run(){
    // 스레드가 실행할 코드;
    }
}
```



### 3. Handler

> 스레드 간의 통신 장치, 핸들러

##### 1.1. 핸들러란

- `Handler` 클래스로 표현되는 핸들러는 스레드 간에 메시지나 `runnable` 객체를 통해 메시지를 주고 받는 장치
- 핸들러는 항상 하나의 스레드와 관련을 맺는데 자신을 생성하는 스레드에 부착되며 그 스레드의 메시지 큐를 통해 다른 스레드와 통신함
- 보통 다른 스레드에서 전달되는 메시지를 수신하지만 자기 자신이 보낸 메시지도 물론 받을 수 있음
- 메시지가 도착하면 핸들러의 다음 메서드가 호출되며 이 메서드를 통해 메시지를 수신


```java
void handleMessage (Message msg)
```

##### 1.2. 핸들러의 필요성

![thread & handler](http://cfile4.uf.tistory.com/image/232EB335577C080F21D36D)


- 백그라운드 스레드는 내부적인 연산이나 해야 하며 다른 스레드 소속의 UI를 건드릴 수 없음
- 내부 클래스에서 외부 클래스의 멤버를 참조하는 것은 자바 문법적으로 합당한 일이지만 다른 스레드 소속의 객체를 참조하는 것은 허락되지 않음
- 왜냐하면 한 객체를 두 스레드가 동시에 변경하는 것을 허용하면 골치 아픈 동기화 문제가 발생하기 떄문


##### 1.3. 핸들러 구현

###### 1.3.1. 핸들러 객체 생성

```java
 class BackThread extends Thread {
        @Override
        public void run() {
            while(true){
                backValue++;
                Log.i("thread", "thread =========" + backValue);
				// 2. 핸들러 객체를 참조하여 메세지를 전달 받는다. 
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
	// 1. 핸들러 객체를 생성 후, 메소드 로직 작성
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == 0){

                backText.setText("backValue : " + backValue);
            }
        }
    };
```

###### 1.3.2. 익명 객체 구현

```java
 class BackThread extends Thread {
        @Override
        public void run() {
            while(true){
                backValue++;
                Log.i("thread", "thread =========" + backValue);
              	// 2. 핸들러.post(new Runnalbe()) { run(); 메소드 작성}; 
              	// 익명 객체를 활용하여 코드를 줄일 수 있음
              	// 2.1 ================================
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        backText.setText("backValue : " + backValue);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
              
              // ========================================= 2.1

            }
        }
    }
	// 1. 핸들러 객체만 생성 (핸들러 객체가 메시지를 직접 처리하지 않을 경우, 즉 메서드 내 로직이 필요 없는 경우에 해당)
	// 1.1. 핸들러 객체가 처리해야할 일이 있다면 메서드 내에 로직을 작성해줘야 함. 
    Handler handler = new Handler();
}
```

>  `runOnUiThread();` 를 활용하면 `2.1.부분의 코드`를 더 짧게 줄일 수 있다.

```java
class BackThread extends Thread {
        @Override
        public void run() {
            while(true){
                backValue++;
                Log.i("thread", "thread =========" + backValue);       
              	// =========== 바로 Ui thread의 객체(Widget)에 접근 가능하다.
               	runOnUiThread(new Runnable){
                  @Override
                    public void run() {
                        backText.setText("backValue : " + backValue);
                    }  
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }           
            }
        }
    }
	// * handler의 역할을 runOnUiThread();가 대신 해준다.
    // Handler handler = new Handler();
}

```



##### 1.4. Message

>메시지는 `handler`에게 전달할 수 있는 코드로 된 묘사체이며, 임의의 데이터 객체이다. `message`객체는 두 개의 int field와 한 개의 object를 포함하고 있다. 
>
>[출저 : 안드로이드 공식 API 문서]



### 4. Looper

##### 1.4.1. 루퍼란

>큐(Queue)에 저장된 Message를 핸들러에게 전달해주는 작업을 하는 것이 Looper의 역할
>
>위에서 다룬 개념들의 관계를 종합해보면 아래 이미지와 같다.

![Relationship with Thread, Handler and Looper](http://cfile10.uf.tistory.com/image/265E4A4D5104DD650B871F)

- ui를 관리하는 메인 스레드는 기본적으로 루퍼를 가짐
- 따라서 핸들러만 생성하면 메시지를 전달 받을 수 있음
- 작업 스레드는 기본적으로 루퍼를 갖고 있지 않으며 `run();` 메소드 코드만 실행하고 종료됨

##### 1.4.2. 루퍼 구현 방법

- 메인 스레드 - 별도로 루퍼를 생성하지 않아도 갖고 있음

- 작업 스레드 - 루퍼 객체 생성 작업 후, 아래 메소드를 사용

  ###### [자주 사용하는 루퍼 메소드]

```java
static void prepare();
```

- 현재 스레드를 위한 루퍼 준비
- 내부적으로는 메시지를 저장하는 큐를 생성하고, 그외 메시지 전송에 필요한 조치를 함

```java
static void loop();
```

- 큐에서 메시지를 꺼내 핸들러로 전달하는 루프를 실행함

```java
void quit();
```

[스레드와 루퍼를 구하는 메소드]

```java
// === [루퍼와 연결된 스레드를 구함] ===
// 루퍼는 반드시 하나의 스레드와 연관되므로 연결된 스레드가 리턴됨
Thread getThread(); 

// === [스레드에서 연결된 루퍼를 구함] ===
// 어플리케이션 주 스레드의 루퍼를 구함
static Looper getMainLooper(); 
// 현재 스레드의 루퍼를 구함
// 모든 스레드가 루퍼를 갖고 있는 것이 아니므로 null 이 리턴될 수도 있음
static Looper myLooper();
```







### 










