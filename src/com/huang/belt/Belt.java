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
	 * 左边的padding
	 */
	private static int PADDING_LEFT =10;
	/**
	 * 上方的padding
	 */
	private static int TOP_PADDING;
	/**
	 * 按钮的长度
	 */
	private int ButtonWidth ;
	private int ButtonHeight ;
	private Context ctx;
	int lastX ;//记录上次点击位置x坐标
	
	public Belt(Context context)
	{
		this(context,null);//调用下面的构造器
	}
	
	public Belt(Context context, AttributeSet attrs)
	{
		this(context,attrs,-1);//调用下面的构造器
	}

	public Belt(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.ctx = context;
		setWillNotDraw (false);//需要这个 否则ondraw()无法执行
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
			// 测量child
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
		int n =  getChildCount();//有几个child
		Log.d("huang", " 屏幕宽度="+getMeasuredWidth()+" 屏幕长度="+getMeasuredHeight());
		Log.d("huang", " left="+left+" top="+top+" right="+right+" bottom="+bottom);
		Log.d("huang","onLayout getChildCount="+ getChildCount());
		if(changed == true)
		{
			for(int i = 0;i < n;i++)
			{
				final Button child = (Button) getChildAt(i);
				child.setVisibility(View.VISIBLE);
				if(i < (n-2) / 2)//布局第一行按钮：1-2-3-4
				{
					
					child.layout(PADDING_LEFT + left + ((i+1)*ButtonWidth) ,top
							,PADDING_LEFT + left + ((i+2)*ButtonWidth),top + ButtonHeight);
					child.setGravity(Gravity.CENTER_HORIZONTAL);
				}
				else if(i == ((n -2) / 2) )//布局按钮5
				{
					child.layout(PADDING_LEFT + left + ((i+1)*ButtonWidth) ,top + ButtonHeight
							,PADDING_LEFT + left + ((i+2)*ButtonWidth),top + 2 * ButtonHeight);
					child.setGravity(Gravity.CENTER_HORIZONTAL);
				}
				else if(i > ((n -2) / 2) && i < n -1)//布局按钮6-7-8-9
				{
					
					child.layout(PADDING_LEFT + left + ((n-i-1)*ButtonWidth) ,top + 2 * ButtonHeight
							,PADDING_LEFT + left + ((n-i)*ButtonWidth),top + 3 * ButtonHeight);
					child.setGravity(Gravity.CENTER_HORIZONTAL);
					
				}
				else if(i >= n-1 )//布局10
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
						Toast.makeText(ctx, "你点击了"+child.getText(), Toast.LENGTH_SHORT).show();
					}
				});
			}
			
		}
		
	}


	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		  //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        
		switch(event.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            
            lastX = x;
            
            break;
            
        case MotionEvent.ACTION_UP:
        	
            //计算移动的距离
            int offX = x - lastX;
            if(offX > 100)
            {
            	//向右滑动手势 todo
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
		linePaint.setColor(Color.BLACK);//线的颜色
		linePaint.setStrokeWidth(5);//线宽度
		//绘制上线 1-2-3-4
		canvas.drawLine(PADDING_LEFT+ (ButtonWidth * 3 / 2), TOP_PADDING+ButtonHeight/2, PADDING_LEFT+(ButtonWidth * 9 /2), TOP_PADDING+ButtonHeight/2, linePaint);//绘制线
		//下线9-8-7-6
		canvas.drawLine(PADDING_LEFT+ (ButtonWidth * 3 / 2), TOP_PADDING+(ButtonHeight*5/2), PADDING_LEFT+(ButtonWidth * 9 /2), TOP_PADDING+(ButtonHeight*5/2), linePaint);//绘制线
		//斜线10-1
		canvas.drawLine(PADDING_LEFT+ (ButtonWidth  / 2), TOP_PADDING+(ButtonHeight*3/2), PADDING_LEFT+(ButtonWidth * 3 /2), TOP_PADDING+(ButtonHeight/2), linePaint);//绘制线
		//斜线4-5
		canvas.drawLine(PADDING_LEFT+(ButtonWidth * 9 /2), TOP_PADDING+ButtonHeight/2, PADDING_LEFT+(ButtonWidth * 11 /2), TOP_PADDING+(ButtonHeight*3/2), linePaint);//绘制线
		//斜线5-6
		canvas.drawLine(PADDING_LEFT+(ButtonWidth * 11 /2), TOP_PADDING+(ButtonHeight*3/2), PADDING_LEFT+(ButtonWidth * 9 /2), TOP_PADDING+(ButtonHeight*5/2), linePaint);//绘制线
		//斜线9-10
		canvas.drawLine(PADDING_LEFT+ (ButtonWidth * 3 / 2), TOP_PADDING+(ButtonHeight*5/2), PADDING_LEFT+ (ButtonWidth *1 / 2), TOP_PADDING+(ButtonHeight*3/2), linePaint);//绘制线

	}
}

