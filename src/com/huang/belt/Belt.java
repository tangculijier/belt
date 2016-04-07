package com.huang.belt;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Belt extends ViewGroup
{
	final String  TAG = "huang";
	/**
	 * ��ߵ�padding
	 */
	private static int PADDING_LEFT =10;
	/**
	 * �Ϸ���padding
	 */
	private static int TOP_PADDING;
	/**
	 * ��ť�ĳ���
	 */
	private int ButtonWidth ;
	private int ButtonHeight ;
	private Context ctx;
	int lastX ;//��¼�ϴε��λ��x����
	
	public Belt(Context context)
	{
		this(context,null);//��������Ĺ�����
	}
	
	public Belt(Context context, AttributeSet attrs)
	{
		this(context,attrs,-1);//��������Ĺ�����
	}

	public Belt(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.ctx = context;
		setWillNotDraw (false);//��Ҫ��� ����ondraw()�޷�ִ��
		TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.belt,defStyle,0);

		PADDING_LEFT = (int) array.getDimension(R.styleable.belt_belt_padding, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
						getResources().getDisplayMetrics()));
		array.recycle();
	}



	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int childCount = getChildCount();
		Log.d("huang","onMeasure child1="+ childCount);
		for (int i = 0; i < childCount; i++)
		{
			// ����child
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}



	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		TOP_PADDING = top;
		ButtonWidth  = (getMeasuredWidth() - 2 * PADDING_LEFT) / 6;
		ButtonHeight = getChildAt(0).getMeasuredHeight();
		int n =  getChildCount();//�м���child
		Log.d("huang", " ��Ļ���="+getMeasuredWidth()+" ��Ļ����="+getMeasuredHeight());
		Log.d("huang", " left="+left+" top="+top+" right="+right+" bottom="+bottom);
		Log.d("huang","onLayout getChildCount="+ getChildCount());
		if(changed == true)
		{
			for(int i = 0;i < n;i++)
			{
				final Button child = (Button) getChildAt(i);
				child.setVisibility(View.VISIBLE);
				if(i < (n-2) / 2)//���ֵ�һ�а�ť��1-2-3-4
				{
					
					child.layout(PADDING_LEFT + left + ((i+1)*ButtonWidth) ,top
							,PADDING_LEFT + left + ((i+2)*ButtonWidth),top + ButtonHeight);
					child.setGravity(Gravity.CENTER_HORIZONTAL);
				}
				else if(i == ((n -2) / 2) )//���ְ�ť5
				{
					child.layout(PADDING_LEFT + left + ((i+1)*ButtonWidth) ,top + ButtonHeight
							,PADDING_LEFT + left + ((i+2)*ButtonWidth),top + 2 * ButtonHeight);
					child.setGravity(Gravity.CENTER_HORIZONTAL);
				}
				else if(i > ((n -2) / 2) && i < n -1)//���ְ�ť6-7-8-9
				{
					
					child.layout(PADDING_LEFT + left + ((n-i-1)*ButtonWidth) ,top + 2 * ButtonHeight
							,PADDING_LEFT + left + ((n-i)*ButtonWidth),top + 3 * ButtonHeight);
					child.setGravity(Gravity.CENTER_HORIZONTAL);
					
				}
				else if(i >= n-1 )//����10
				{
					child.layout(PADDING_LEFT + left  ,top + ButtonHeight
							,PADDING_LEFT + left + 1*ButtonWidth,top + 2 * ButtonHeight);
					child.setGravity(Gravity.CENTER_HORIZONTAL);
				}
				
				child.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Toast.makeText(ctx, "������"+child.getText(), Toast.LENGTH_SHORT).show();
					}
				});
			}
			
		}
		
	}


	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		  //��ȡ����ָ���ĺ������������
        int x = (int) event.getX();
        int y = (int) event.getY();
        
		switch(event.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            
            lastX = x;
            
            break;
            
        case MotionEvent.ACTION_UP:
        	
            //�����ƶ��ľ���
            int offX = x - lastX;
            if(offX > 100)
            {
            	//���һ������� todo
            	 Log.d(TAG, "onFling right");
            }
            else if(offX < -100)
            {
            	 Log.d(TAG, "onFling left");
            }

           
            break;
        }
        
        return true;
    }
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Paint linePaint = new Paint();
		linePaint.setColor(Color.BLACK);//�ߵ���ɫ
		linePaint.setStrokeWidth(5);//�߿��
		//�������� 1-2-3-4
		canvas.drawLine(PADDING_LEFT+ (ButtonWidth * 3 / 2), TOP_PADDING+ButtonHeight/2, PADDING_LEFT+(ButtonWidth * 9 /2), TOP_PADDING+ButtonHeight/2, linePaint);//������
		//����9-8-7-6
		canvas.drawLine(PADDING_LEFT+ (ButtonWidth * 3 / 2), TOP_PADDING+(ButtonHeight*5/2), PADDING_LEFT+(ButtonWidth * 9 /2), TOP_PADDING+(ButtonHeight*5/2), linePaint);//������
		//б��10-1
		canvas.drawLine(PADDING_LEFT+ (ButtonWidth  / 2), TOP_PADDING+(ButtonHeight*3/2), PADDING_LEFT+(ButtonWidth * 3 /2), TOP_PADDING+(ButtonHeight/2), linePaint);//������
		//б��4-5
		canvas.drawLine(PADDING_LEFT+(ButtonWidth * 9 /2), TOP_PADDING+ButtonHeight/2, PADDING_LEFT+(ButtonWidth * 11 /2), TOP_PADDING+(ButtonHeight*3/2), linePaint);//������
		//б��5-6
		canvas.drawLine(PADDING_LEFT+(ButtonWidth * 11 /2), TOP_PADDING+(ButtonHeight*3/2), PADDING_LEFT+(ButtonWidth * 9 /2), TOP_PADDING+(ButtonHeight*5/2), linePaint);//������
		//б��9-10
		canvas.drawLine(PADDING_LEFT+ (ButtonWidth * 3 / 2), TOP_PADDING+(ButtonHeight*5/2), PADDING_LEFT+ (ButtonWidth *1 / 2), TOP_PADDING+(ButtonHeight*3/2), linePaint);//������

	}
}

